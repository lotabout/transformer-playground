package me.lotabout.processor.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import com.google.common.collect.ImmutableList;

import me.lotabout.annotation.Transformer;

public interface TypeEntry {
    String getQualifiedName();
    String getName();
    String getFullName();
    String getPackageName();

    List<FieldEntry> getAllFields();
    List<MethodEntry> getAllMethods();

    boolean isPrimitive();
    boolean isCollection();
    boolean isMap();
    boolean isBoolean();

    // two types should be the same to make it return true
    boolean ableToTransformDirectlyTo(TypeEntry to);

    // two classes should implement transformer class to make it return true
    boolean ableToTransformByTransformerTo(TypeEntry to);

    default boolean ableToTransformTo(TypeEntry to) {
        return this.ableToTransformDirectlyTo(to) || this.ableToTransformByTransformerTo(to);
    }

    Optional<AnnotationMirror> getAnnotationMirror(Class<?> clazz);

    static Optional<AnnotationValue> getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet() ) {
            if(entry.getKey().getSimpleName().toString().equals(key)) {
                return Optional.ofNullable(entry.getValue());
            }
        }
        return Optional.empty();
    }

    static List<TypeEntry> getTransformerClasses(TypeEntry clazz, String key) {
        return clazz.getAnnotationMirror(Transformer.class)
                .flatMap(annotation -> TypeEntry.getAnnotationValue(annotation, key))
                .map(annotation -> (List<AnnotationValue>)annotation.getValue())
                .map(classes -> classes.stream()
                        .map(annotationClass -> (TypeMirror)annotationClass.getValue())
                        .map(EntryFactory::of)
                        .collect(Collectors.toList()))
                .orElse(ImmutableList.of());
    }

    default String transformTo(TypeEntry to, String value) {
        return transformTo(to, value, 1);
    }

    // level is for List or Map. List<List<...>>, level will be used to generate variable names
    String transformTo(TypeEntry to, String value, int level);
    Set<String> getImports();
}
