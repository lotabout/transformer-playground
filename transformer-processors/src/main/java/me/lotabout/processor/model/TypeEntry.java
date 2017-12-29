package me.lotabout.processor.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

public interface TypeEntry {
    String getQualifiedName();
    String getName();
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

    Optional<AnnotationMirror> getAnnotationMirror(Class<?> clazz);

    static Optional<AnnotationValue> getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet() ) {
            if(entry.getKey().getSimpleName().toString().equals(key)) {
                return Optional.ofNullable(entry.getValue());
            }
        }
        return Optional.empty();
    }
}
