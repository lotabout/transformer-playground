package me.lotabout.processor.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransformerMethod {
    private String modifier = "private";
    private String name;
    private TypeEntry from;
    private TypeEntry to;
    private Map<String, FieldEntry> fromFields;
    private Map<String, FieldEntry> toFields;

    TransformerMethod (TypeEntry from, TypeEntry to, String name, boolean isPrivate) {
        this.from = from;
        this.to = to;
        this.name = name;
        this.modifier = isPrivate ? "private" : "public";

        this.fromFields = from.getAllFields().stream().collect(Collectors.toMap(FieldEntry::getName, Function.identity()));
        this.toFields = to.getAllFields().stream().collect(Collectors.toMap(FieldEntry::getName, Function.identity()));
    }

    public String getModifier() {
        return modifier;
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

    public List<String> getCommonFieldNames() {
        // must retain the order of fields;
        Set<String> fromFields = new HashSet<>(this.fromFields.keySet());
        return this.to.getAllFields().stream()
                .map(FieldEntry::getName)
                .filter(fromFields::contains)
                .collect(Collectors.toList());
    }

    public List<String> getFromOnlyFieldNames() {
        Set<String> toFields = new HashSet<>(this.toFields.keySet());
        return this.from.getAllFields().stream()
                .map(FieldEntry::getName)
                .filter(f -> !toFields.contains(f))
                .collect(Collectors.toList());
    }
    public List<String> getToOnlyFieldNames() {
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

    public static TransformerMethod of(TypeEntry from, TypeEntry to) {
        // TODO: lower the first letter of the method name
        return new TransformerMethod(from, to, from.getName() + "2" + to.getName(), true);
    }

    public static TransformerMethod of(TypeEntry from, TypeEntry to, String methodName) {
        return new TransformerMethod(from, to, methodName, false);
    }
}
