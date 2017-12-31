package me.lotabout.processor.model;

import com.squareup.javapoet.ClassName;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                && innerClassesOfA.get(1).ableToTransformTo(innerClassesOfB.get(1));
    }

    @Override public String transformTo(TypeEntry to, String value, List<Object> params, int level) {
        TypeEntry innerClassOfA = getBoundedClass(this).get(1);
        TypeEntry innerClassOfB = getBoundedClass((MapClassEntry)to).get(1);

        String keyVar = "k" + String.valueOf(level);
        String valVar = "v" + String.valueOf(level);
        ClassName collectors = ClassName.get(Collectors.class);

        List<Object> innerParams = new ArrayList<>();
        String transformer = innerClassOfA.transformTo(innerClassOfB, valVar + ".getValue()", innerParams, level+1);
        params.add(collectors);
        params.addAll(innerParams);

        return String.format("%s.entrySet().stream().collect($T.toMap(%s -> %s.getKey(), %s -> %s))",
                value, keyVar, keyVar, valVar, transformer);
    }

    private static List<TypeEntry> getBoundedClass(MapClassEntry clazz) {
        return ((DeclaredType)clazz.getRaw()).getTypeArguments()
                .stream()
                .map(EntryFactory::of)
                .collect(Collectors.toList());
    }
}
