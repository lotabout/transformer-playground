package me.lotabout.processor.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.type.TypeMirror;

import com.google.common.collect.ImmutableList;

import me.lotabout.annotation.Transformer;

public class ConcreteClassEntry extends AbstractClassEntry {
    public ConcreteClassEntry(TypeMirror raw) {
        super(raw);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public boolean isMap() {
        return false;
    }

    @Override public boolean ableToTransformDirectlyTo(TypeEntry from) {
        return this.getQualifiedName().equals(from.getQualifiedName());
    }

    @Override
    public boolean ableToTransformByTransformerTo(TypeEntry to) {
        if (!(to instanceof ConcreteClassEntry)) {
            return false;
        }

        // Transform: A -> B
        //        (this)   (to)

        List<TypeEntry> toClassesOfA= getTransformerClasses(this, "to");
        List<TypeEntry> fromClassesOfB = getTransformerClasses(to, "from");

        return (toClassesOfA.stream().anyMatch(t -> t.getQualifiedName().equals(to.getQualifiedName()))
                || fromClassesOfB.stream().anyMatch(t -> t.getQualifiedName().equals(this.getQualifiedName())));
    }

    private static List<TypeEntry> getTransformerClasses(TypeEntry clazz, String key) {
        return clazz.getAnnotationMirror(Transformer.class)
                .flatMap(annotation -> TypeEntry.getAnnotationValue(annotation, key))
                .map(annotation -> (List<AnnotationValue>)annotation.getValue())
                .map(classes -> classes.stream()
                        .map(annotationClass -> (TypeMirror)annotationClass.getValue())
                        .map(EntryFactory::of)
                        .collect(Collectors.toList()))
                .orElse(ImmutableList.of());
    }
}
