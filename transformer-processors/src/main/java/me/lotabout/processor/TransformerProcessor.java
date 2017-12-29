package me.lotabout.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import me.lotabout.processor.model.EntryFactory;
import me.lotabout.processor.model.FieldEntry;
import me.lotabout.processor.model.TypeEntry;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import me.lotabout.annotation.Transformer;
import me.lotabout.processor.model.TransformerMethod;

@SupportedAnnotationTypes("me.lotabout.annotation.Transformer")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TransformerProcessor extends AbstractProcessor {
    private final String transformerTemplate = "template/TransformerTemplate.vm";

    public TransformerProcessor() {
        super();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        if (SourceVersion.latest().compareTo(SourceVersion.RELEASE_8) > 0) {
            return SourceVersion.latest();
        } else {
            return SourceVersion.RELEASE_8;
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(Transformer.class.getCanonicalName());
    }

    private void debug(Element e, String message, Object... objects) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(message, objects), e);
    }

    private void error(Element e, String message, Object... objects) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(message, objects), e);
    }

    @Override public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(Transformer.class)
                .stream()
                .filter(TypeElement.class::isInstance)
                .map(TypeElement.class::cast)
                .forEach(this::generateTransformer);
        return true;
    }

    private void generateTransformer(TypeElement orig) {
        AnnotationMirror transformer = getAnnotationMirror(orig, Transformer.class);
        TypeEntry clazz = EntryFactory.of(orig.asType());

        // get the classes from annotation
        List<TypeMirror> fromTypes = Optional.ofNullable(getAnnotationValue(transformer, "from"))
                .map(annotation -> (List<AnnotationValue>)annotation.getValue())
                .map(classes -> classes.stream()
                        .map(annotationClass -> (TypeMirror)annotationClass.getValue())
                        .collect(Collectors.toList()))
                .orElse(ImmutableList.of());
        List<TypeMirror> toTypes = Optional.ofNullable(getAnnotationValue(transformer, "to"))
                .map(annotation -> (List<AnnotationValue>)annotation.getValue())
                .map(classes -> classes.stream()
                        .map(annotationClass -> (TypeMirror)annotationClass.getValue())
                        .collect(Collectors.toList()))
                .orElse(ImmutableList.of());


        // generate method for each type
        List<TransformerMethod> transformerMethods = new ArrayList<>();

        fromTypes.forEach(t -> {
            TypeEntry fromClass = EntryFactory.of(t);
            transformerMethods.add(TransformerMethod.of(fromClass, clazz, "from"+fromClass.getName()));
        });

        toTypes.forEach(t -> {
            TypeEntry toClass = EntryFactory.of(t);
                transformerMethods.add(TransformerMethod.of(toClass, clazz, "to"+toClass.getName()));
        });

        // write contents
        VelocityContext context = new VelocityContext();
        context.put("clazz", clazz);
        context.put("transformers", transformerMethods);

        outputSourceCode(transformerTemplate, context, clazz.getName() + "Transformer", orig);
    }

    private List<TransformerMethod> collectSubTransformers(TransformerMethod method) {
        List<TransformerMethod> ret = new ArrayList<>();

        List<String> commonFieldNames = method.getCommonFieldNames();
        Map<String, FieldEntry> fromFields = method.getFromFields();
        Map<String, FieldEntry> toFields= method.getToFields();
        for (String fieldName: commonFieldNames) {
            FieldEntry from = fromFields.get(fieldName);
            FieldEntry to = toFields.get(fieldName);

//            if (!from.getType().needTransform(to)) {
//                continue;
//            }
//
//            // Not able to generate transformation method
//            if ((from.isPrimitive() || to.isPrimitive())) {
//                error(method.getFrom().getRaw(), "type mismatch of field %s(%s -> %s)", fieldName, from.getTypeName(), to.getTypeName());
//            }
//
//            // is collection, but collection type mismatch
//            if (from.getType().isCollection() && !to.getType().isCollection()
//                    || !from.getType().isCollection() && to.getType().isCollection()
//                    || from.getType().isMap() && !to.getType().isMap()
//                    || !from.getType().isMap() && to.getType().isMap()) {
//                error(method.getFrom().getRaw(), "type mismatch of field %s(%s -> %s)", fieldName, from.getTypeName(), to.getTypeName());
//            }
//
//            if (from.getType().isCollection() && to.getType().isCollection()) {
//
//            }
//
//            if (from.getType().isMap() && to.getType().isMap()) {
//
//            }
//
//            // transform from class to class, e.g. XXXDTO -> XXXPOJO
//            ret.add(TransformerMethod.of(from.getType(), to.getType()));
        }

        return ret;
    }

    private void outputSourceCode(String templateName, VelocityContext context, String sourceFileName, TypeElement e) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();


        final Filer filer = processingEnv.getFiler();
        try (OutputStream outputStream = filer.createSourceFile(sourceFileName, e).openOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(outputStream, Charsets.UTF_8);
                PrintWriter writer = new PrintWriter(osw)) {
            Template template = velocityEngine.getTemplate(templateName);
            template.merge(context, writer);
        } catch (IOException ex) {
            throw new TransformerProcessingException(e, "could not output processing result to file '"
                    + sourceFileName
                    + "'.", ex);
        }
    }

    private static AnnotationMirror getAnnotationMirror(TypeElement typeElement, Class<?> clazz) {
        String clazzName = clazz.getName();
        for(AnnotationMirror m : typeElement.getAnnotationMirrors()) {
            if(m.getAnnotationType().toString().equals(clazzName)) {
                return m;
            }
        }
        return null;
    }

    private static AnnotationValue getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet() ) {
            if(entry.getKey().getSimpleName().toString().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private TypeElement asTypeElement(TypeMirror typeMirror) {
        return (TypeElement)this.processingEnv.getTypeUtils().asElement(typeMirror);
    }
}
