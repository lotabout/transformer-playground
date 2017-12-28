package me.lotabout.processor.model;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.List;

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

    @Override
    public boolean ableToTransformByMethodFrom(TypeEntry from) {
        return this.getQualifiedName().equals(from.getQualifiedName());
    }

    @Override
    public boolean needTransformMethodFrom(TypeEntry from) {
        return false;
    }
}
