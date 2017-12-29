package me.lotabout.processor.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public class MapClassEntry extends AbstractClassEntry {
    public MapClassEntry(TypeMirror raw) {
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

    @Override public boolean ableToTransformDirectlyTo(TypeEntry to) {
        return false;
    }

    @Override
    public boolean ableToTransformByTransformerTo(TypeEntry to) {
        if (!(to instanceof MapClassEntry)) {
            return false;
        }
        // check inner types could be transformed
        List<TypeEntry> innerClassesOfA = getBoundedClass(this);
        List<TypeEntry> innerClassesOfB = getBoundedClass((MapClassEntry)to);
        return innerClassesOfA.get(0).ableToTransformDirectlyTo(innerClassesOfB.get(0))
                && innerClassesOfA.get(1).ableToTransformByTransformerTo(innerClassesOfB.get(1));
    }

    private static List<TypeEntry> getBoundedClass(MapClassEntry clazz) {
        return ((DeclaredType)clazz.getRaw()).getTypeArguments()
                .stream()
                .map(EntryFactory::of)
                .collect(Collectors.toList());
    }
}
