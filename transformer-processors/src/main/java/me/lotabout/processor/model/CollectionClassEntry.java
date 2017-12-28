package me.lotabout.processor.model;

import javax.lang.model.type.TypeMirror;

class CollectionClassEntry extends AbstractClassEntry {
    public CollectionClassEntry(TypeMirror raw) {
        super(raw);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isCollection() {
        return true;
    }

    @Override
    public boolean isMap() {
        return false;
    }

    @Override
    public boolean ableToTransformByMethodFrom(TypeEntry from) {
        return false;
    }

    @Override
    public boolean needTransformMethodFrom(TypeEntry from) {
        return false;
    }
}
