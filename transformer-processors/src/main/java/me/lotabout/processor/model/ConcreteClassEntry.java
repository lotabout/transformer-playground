package me.lotabout.processor.model;

import javax.lang.model.type.TypeMirror;

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

    @Override
    public boolean ableToTransformByMethodFrom(TypeEntry from) {
        return from instanceof ConcreteClassEntry;
    }

    @Override
    public boolean needTransformMethodFrom(TypeEntry from) {
        return !this.getQualifiedName().equals(from.getQualifiedName());
    }

}
