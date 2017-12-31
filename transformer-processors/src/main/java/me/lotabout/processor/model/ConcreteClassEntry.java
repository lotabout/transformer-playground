package me.lotabout.processor.model;

import com.squareup.javapoet.ClassName;

import javax.lang.model.type.TypeMirror;
import java.util.List;

public class ConcreteClassEntry extends AbstractClassEntry {
    public ConcreteClassEntry(TypeMirror raw) {
        super(raw);
    }

    @Override
    public boolean ableToTransformDirectlyTo(TypeEntry from) {
        return this.getQualifiedName().equals(from.getQualifiedName());
    }

    @Override
    public boolean ableToTransformByTransformerTo(TypeEntry to) {
        if (!(to instanceof ConcreteClassEntry)) {
            return false;
        }

        // Transform: A -> B
        //        (this)   (to)

        List<TypeEntry> toClassesOfA = TypeEntry.getTransformerClasses(this, "to");
        List<TypeEntry> fromClassesOfB = TypeEntry.getTransformerClasses(to, "from");

        return (toClassesOfA.stream().anyMatch(t -> t.getQualifiedName().equals(to.getQualifiedName()))
                || fromClassesOfB.stream().anyMatch(t -> t.getQualifiedName().equals(this.getQualifiedName())));
    }

    @Override
    public String transformTo(TypeEntry to, String value, List<Object> params, int level) {
        assert this.ableToTransformDirectlyTo(to) || this.ableToTransformByTransformerTo(to);
        // From A -> B
        //   (this) (to)

        if (this.ableToTransformDirectlyTo(to)) {
            // no transformation is needed
            return value;
        }

        // able to transform via transformer

        List<TypeEntry> toClassesOfA = TypeEntry.getTransformerClasses(this, "to");
        if (toClassesOfA.stream().anyMatch(t -> t.getQualifiedName().equals(to.getQualifiedName()))) {
            ClassName transformer = ClassName.get(this.getPackageName(), String.format("%sTransformer", this.getName()));
            params.add(0, transformer);
            return String.format("$T.to%s(%s)", to.getName(), value);
        } else {
            ClassName transformer = ClassName.get(to.getPackageName(), String.format("%sTransformer", to.getName()));
            params.add(0, transformer);
            return String.format("$T.from%s(%s)", this.getName(), value);
        }
    }
}
