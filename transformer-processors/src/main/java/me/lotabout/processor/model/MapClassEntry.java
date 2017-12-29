package me.lotabout.processor.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public class MapClassEntry extends AbstractClassEntry {
    public MapClassEntry(TypeMirror raw) {
        super(raw);
    }

    @Override
    public String getFullName() {
        List<TypeEntry> innerClasses = getBoundedClass(this);;
        TypeEntry keyType = innerClasses.get(0);
        TypeEntry valueType = innerClasses.get(1);
        return this.getName() + "<" + keyType.getFullName() + ", " + valueType.getFullName() + ">";
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

    @Override public String transformTo(TypeEntry to, String value) {
        TypeEntry innerClassOfA = getBoundedClass(this).get(1);
        TypeEntry innerClassOfB = getBoundedClass((MapClassEntry)to).get(1);

        return value + ".entrySet().stream().collect(Collectors.toMap(k -> k, v -> "
                + innerClassOfA.transformTo(innerClassOfB, "v")
                + "))";
    }

    @Override public Set<String> getImports() {
        List<TypeEntry> innerClasses = getBoundedClass(this);;
        TypeEntry keyType = innerClasses.get(0);
        TypeEntry valueType = innerClasses.get(1);

        Set<String> ret = new HashSet<>();
        ret.add("java.util.stream.Collectors");
        ret.add(this.getQualifiedName());
        ret.add(keyType.getQualifiedName());
        ret.addAll(valueType.getImports());
        return ret;
    }

    private static List<TypeEntry> getBoundedClass(MapClassEntry clazz) {
        return ((DeclaredType)clazz.getRaw()).getTypeArguments()
                .stream()
                .map(EntryFactory::of)
                .collect(Collectors.toList());
    }
}
