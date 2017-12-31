package me.lotabout.processor.model;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PrimitiveEntry implements TypeEntry {
    private TypeMirror self;

    public PrimitiveEntry(TypeMirror self) {
        this.self = self;
    }

    @Override
    public String getQualifiedName() {
        return self.toString();
    }

    @Override
    public String getName() {
        return self.toString();
    }

    @Override
    public String getFullName() {
        return this.getName();
    }

    @Override
    public String getPackageName() {
        return "";
    }

    @Override
    public List<FieldEntry> getAllFields() {
        return Collections.emptyList();
    }

    @Override
    public List<MethodEntry> getAllMethods() {
        return Collections.emptyList();
    }

    @Override
    public TypeMirror getRaw() {
        return self;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public boolean isMap() {
        return false;
    }

    @Override
    public boolean isBoolean() {
        return self.getKind() == TypeKind.BOOLEAN;
    }

    @Override public boolean ableToTransformDirectlyTo(TypeEntry to) {
        return this.getQualifiedName().equals(to.getQualifiedName());
    }

    @Override
    public boolean ableToTransformByTransformerTo(TypeEntry to) {
        return false;
    }

    @Override public Optional<AnnotationMirror> getAnnotationMirror(Class<?> clazz) {
        return Optional.empty();
    }

    @Override public String transformTo(TypeEntry to, String value, int level) {
        assert this.ableToTransformDirectlyTo(to);
        return value;
    }

    @Override public Set<String> getImports() {
        return Collections.emptySet();
    }
}
