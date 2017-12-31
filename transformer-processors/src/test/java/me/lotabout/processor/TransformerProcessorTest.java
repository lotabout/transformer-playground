package me.lotabout.processor;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;


public class TransformerProcessorTest {
    private final static String ResourceDir = TransformerProcessorTest.class.getPackage().getName().replace(".", "/");

    @Test
    public void nothingShouldBeGeneratedIfNoAnnotation() {
        String topic = "noannotation";
        Truth.assert_()
                .about(javaSources())
                .that(getResourceFiles(topic, "NoAnnotationBo.java", "NoAnnotationPojo.java"))
                .processedWith(new TransformerProcessor())
                .compilesWithoutError();
    }

    @Test
    public void allMatchedFieldsShouldBeTransformed() {
        String topic = "allmatch";
        Truth.assert_()
                .about(javaSources())
                .that(getResourceFiles(topic, "AllMatchBo.java", "AllMatchPojo.java"))
                .processedWith(new TransformerProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(getResourceFile(topic, "AllMatchBoTransformer.java"));
    }

    @Test
    public void fieldsWithDifferentNameShouldNotBeTransformed() {
        // Note that the test will not test whether the comments in generated files are the same or not.
        String topic = "fieldcheck";
        Truth.assert_()
                .about(javaSources())
                .that(getResourceFiles(topic, "FieldsWithDifferentNameBo.java", "FieldsWithDifferentNamePojo.java"))
                .processedWith(new TransformerProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(getResourceFile(topic, "FieldsWithDifferentNameBoTransformer.java"));
    }

    @Test
    public void classesThatImplementTransformerShouldBeDetected() {
        // Note that the test will not test whether the comments in generated files are the same or not.
        String topic = "impltransformer";
        Truth.assert_()
                .about(javaSources())
                .that(getResourceFiles(topic, "ApplicantBo.java", "ApplicantPojo.java", "EducationVo.java", "EducationPojo.java"))
                .processedWith(new TransformerProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(getResourceFile(topic, "ApplicantBoTransformer.java"), getResourceFile(topic,"EducationPojoTransformer.java"));
    }

    @Test
    public void primitiveTypeShouldNotBeCasted() {
        // Note that the test will not test whether the comments in generated files are the same or not.
        String topic = "typecheck";
        Truth.assert_()
                .about(javaSources())
                .that(getResourceFiles(topic, "DifferentPrimitiveA.java", "DifferentPrimitiveB.java"))
                .processedWith(new TransformerProcessor())
                .failsToCompile()
                .withErrorContaining("Unable to transform fieldA from int -> short");
    }

    @Test
    public void primitiveTypeAndItsWrapperClassShouldNotBeCasted() {
        // Note that the test will not test whether the comments in generated files are the same or not.
        String topic = "typecheck";
        Truth.assert_()
                .about(javaSources())
                .that(getResourceFiles(topic, "PrimitiveAndWrapperClassA.java", "PrimitiveAndWrapperClassB.java"))
                .processedWith(new TransformerProcessor())
                .failsToCompile()
                .withErrorCount(8); // all fields should fail to transform
    }

    @Test
    public void listOfClassShouldDependsOnItsInnerClasses() {
        String topic = "typecheck";
        Truth.assert_()
                .about(javaSources())
                .that(getResourceFiles(topic, "ListWrapperA.java", "ListWrapperB.java"))
                .processedWith(new TransformerProcessor())
                .failsToCompile()
                .withErrorCount(2) // all fields should fail to transform
                .withErrorContaining("Unable to transform list from List<Integer> -> List<String>") ;
    }

    @Test
    public void keyOfMapShouldBeDirectlyTransformable() {
        // e.g. the key of map should be the same class without List/Map wrapper
        String topic = "typecheck";
        Truth.assert_()
                .about(javaSources())
                .that(getResourceFiles(topic, "MapWrapperKeyA.java", "MapWrapperKeyB.java"))
                .processedWith(new TransformerProcessor())
                .failsToCompile()
                .withErrorCount(2) // all fields should fail to transform
                .withErrorContaining("Unable to transform mapWithStrangeKey from Map<List<String>, Integer> -> Map<List<String>, Integer>.") ;
    }

    @Test
    public void valueOfMapShouldBeTransformable() {
        // e.g. the key of map should be the same class without List/Map wrapper
        String topic = "typecheck";
        Truth.assert_()
                .about(javaSources())
                .that(getResourceFiles(topic, "MapWrapperValueA.java", "MapWrapperValueB.java"))
                .processedWith(new TransformerProcessor())
                .failsToCompile()
                .withErrorCount(2) // all fields should fail to transform
                .withErrorContaining("Unable to transform nestedMismatch from Map<String, List<String>> -> Map<String, List<Integer>>. ") ;
    }

    private List<JavaFileObject> getResourceFiles(String topic, String... files) {
        return Arrays.stream(files)
                .map(f -> getResourceFile(topic, f))
                .collect(Collectors.toList());
    }

    private JavaFileObject getResourceFile(String topic, String file) {
        String directory = ResourceDir + "/" + topic + "/";
        return JavaFileObjects.forResource(directory + file);
    }
}
