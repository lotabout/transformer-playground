package me.lotabout.processor.model;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransformerMethod {
    private String name;
    private TypeEntry from;
    private TypeEntry to;
    private Map<String, FieldEntry> fromFields;
    private Map<String, FieldEntry> toFields;

    TransformerMethod (TypeEntry from, TypeEntry to, String name) {
        this.from = from;
        this.to = to;
        this.name = name;

        this.fromFields = from.getAllFields().stream().collect(Collectors.toMap(FieldEntry::getName, Function.identity()));
        this.toFields = to.getAllFields().stream().collect(Collectors.toMap(FieldEntry::getName, Function.identity()));
    }

    public String getName() {
        return name;
    }

    public Map<String, FieldEntry> getFromFields() {
        return fromFields;
    }

    public Map<String, FieldEntry> getToFields() {
        return toFields;
    }

    public TypeEntry getFrom() {
        return from;
    }

    public TypeEntry getTo() {
        return to;
    }

    private List<String> getCommonFieldNames() {
        // must retain the order of fields;
        Set<String> fromFields = new HashSet<>(this.fromFields.keySet());
        return this.to.getAllFields().stream()
                .map(FieldEntry::getName)
                .filter(fromFields::contains)
                .collect(Collectors.toList());
    }

    private List<String> getFromOnlyFieldNames() {
        Set<String> toFields = new HashSet<>(this.toFields.keySet());
        return this.from.getAllFields().stream()
                .map(FieldEntry::getName)
                .filter(f -> !toFields.contains(f))
                .collect(Collectors.toList());
    }
    private List<String> getToOnlyFieldNames() {
        Set<String> fromFields = new HashSet<>(this.fromFields.keySet());
        return this.to.getAllFields().stream()
                .map(FieldEntry::getName)
                .filter(f -> !fromFields.contains(f))
                .collect(Collectors.toList());
    }

    public Set<String> getAllImports() {
        Set<String> ret = new HashSet<>();
        ret.addAll(from.getImports());
        ret.addAll(to.getImports());
        this.getFromFields().values().forEach(field -> ret.addAll(field.getType().getImports()));
        this.getToFields().values().forEach(field -> ret.addAll(field.getType().getImports()));
        return ret;
    }

    public MethodSpec generate(TransformerClass environment) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(this.getName());
        // add method header
        builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get(to.getRaw()))
                .addParameter(ClassName.get(from.getRaw()), "from")
                .addStatement("$T to = new $T();", ClassName.get(to.getRaw()), ClassName.get(to.getRaw()));


        // add method body
        for (String fieldName: getCommonFieldNames()) {
            FieldEntry fromField = fromFields.get(fieldName);
            FieldEntry toField = toFields.get(fieldName);
            fromField.transformTo(toField, environment, builder);
        }

        // add comments

        builder.addComment("Fields only in "+from.getName());
        for (String fieldName: getFromOnlyFieldNames()) {
            FieldEntry fromField = fromFields.get(fieldName);
            builder.addComment("$T: $L", ClassName.get(fromField.getType().getRaw()), fieldName);
        }

        builder.addComment("Fields only in "+to.getName());
        for (String fieldName: getToOnlyFieldNames()) {
            FieldEntry toField = toFields.get(fieldName);
            builder.addComment("$T: $L", ClassName.get(toField.getType().getRaw()), fieldName);
        }

        // build the method
        builder.addStatement("return to");
        return builder.build();
    }

    public static TransformerMethod of(TypeEntry from, TypeEntry to, String methodName) {
        return new TransformerMethod(from, to, methodName);
    }
}
