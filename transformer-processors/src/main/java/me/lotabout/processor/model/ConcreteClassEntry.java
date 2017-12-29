package me.lotabout.processor.model;

import java.util.List;

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

        List<TypeEntry> toClassesOfA= TypeEntry.getTransformerClasses(this, "to");
        List<TypeEntry> fromClassesOfB = TypeEntry.getTransformerClasses(to, "from");

        return (toClassesOfA.stream().anyMatch(t -> t.getQualifiedName().equals(to.getQualifiedName()))
                || fromClassesOfB.stream().anyMatch(t -> t.getQualifiedName().equals(this.getQualifiedName())));
    }

    @Override public String transformTo(TypeEntry to, String value) {
        assert this.ableToTransformDirectlyTo(to) || this.ableToTransformByTransformerTo(to);

        if (this.ableToTransformDirectlyTo(to)) {
            return value;
        }

        // else, able to transform via transformer
        List<TypeEntry> toClassesOfA= TypeEntry.getTransformerClasses(this, "to");
        if (toClassesOfA.stream().anyMatch(t -> t.getQualifiedName().equals(to.getQualifiedName()))) {
            return this.getName()+"Transformer.to"+to.getName()+"("+value+")";
        } else {
            return to.getName()+"Transformer.from"+this.getName()+"("+value+")";
        }
    }
}
