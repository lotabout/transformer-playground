package me.lotabout.processor.model;

import com.squareup.javapoet.ClassName;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;
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

    @Override public String transformTo(TypeEntry to, String value, List<Object> params, int level) {
        assert this.ableToTransformDirectlyTo(to) || this.ableToTransformByTransformerTo(to);

        TypeEntry innerClassOfA = getBoundedClass(this).get(0);
        TypeEntry innerClassOfB = getBoundedClass((CollectionClassEntry)to).get(0);
        String listVar = "l" + String.valueOf(level);
        ClassName collectors = ClassName.get(Collectors.class);

        String transformer = innerClassOfA.transformTo(innerClassOfB, listVar, params, level + 1);
        // the values comes after all params, note that we should call transformer First
        params.add(collectors);

        return String.format("%s.stream().map(%s -> %s).collect($T.toList())", value, listVar, transformer);
    }
}
