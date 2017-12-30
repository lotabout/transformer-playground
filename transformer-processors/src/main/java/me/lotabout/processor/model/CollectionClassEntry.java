package me.lotabout.processor.model;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class CollectionClassEntry extends AbstractClassEntry {
    public CollectionClassEntry(TypeMirror raw) {
        super(raw);
    }

    @Override
    public String getFullName() {
        TypeEntry innerClass = getBoundedClass(this).get(0);
        return this.getName() + "<" + innerClass.getFullName() + ">";
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

    @Override public boolean ableToTransformDirectlyTo(TypeEntry from) {
        return false;
    }

    @Override
    public boolean ableToTransformByTransformerTo(TypeEntry to) {
        if (!(to instanceof CollectionClassEntry)) {
            return false;
        }
        // check inner types could be transformed
        TypeEntry innerClassOfA = getBoundedClass(this).get(0);
        TypeEntry innerClassOfB = getBoundedClass((CollectionClassEntry)to).get(0);
        return innerClassOfA.ableToTransformTo(innerClassOfB);
    }

    private static List<TypeEntry> getBoundedClass(CollectionClassEntry clazz) {
        return ((DeclaredType)clazz.getRaw()).getTypeArguments()
                .stream()
                .map(EntryFactory::of)
                .collect(Collectors.toList());
    }

    @Override public String transformTo(TypeEntry to, String value, int level) {
        assert this.ableToTransformDirectlyTo(to) || this.ableToTransformByTransformerTo(to);

        TypeEntry innerClassOfA = getBoundedClass(this).get(0);
        TypeEntry innerClassOfB = getBoundedClass((CollectionClassEntry)to).get(0);
        String listVar = "l" + String.valueOf(level);

        return String.format("%s.stream().map(%s -> %s).collect(Collectors.toList())",
                value, listVar, innerClassOfA.transformTo(innerClassOfB, listVar, level + 1));
    }

    @Override public Set<String> getImports() {
        Set<String> ret = new HashSet<>();
        ret.add("java.util.stream.Collectors");
        ret.add(this.getQualifiedName());
        TypeEntry innerClass = getBoundedClass(this).get(0);
        ret.addAll(innerClass.getImports());
        return ret;
    }

}
