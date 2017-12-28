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
    private ClassModel from;
    private ClassModel to;
    private Map<String, FieldModel> fromFields;
    private Map<String, FieldModel> toFields;

    TransformerMethod (ClassModel from, ClassModel to, String name, boolean isPrivate) {
        this.from = from;
        this.to = to;
        this.name = name;
        this.modifier = isPrivate ? "private" : "public";

        this.fromFields = from.getAllFields()
                .stream()
                .collect(Collectors.toMap(FieldModel::getName, Function.identity()));
        this.toFields = from.getAllFields()
                .stream()
                .collect(Collectors.toMap(FieldModel::getName, Function.identity()));
    }

    public String getModifier() {
        return modifier;
    }

    public String getName() {
        return name;
    }

    public Map<String, FieldModel> getFromFields() {
        return fromFields;
    }

    public Map<String, FieldModel> getToFields() {
        return toFields;
    }

    public ClassModel getFrom() {
        return from;
    }

    public ClassModel getTo() {
        return to;
    }

    public List<String> getCommonFieldNames() {
        Set<String> commonFields = new HashSet<>(this.fromFields.keySet());
        commonFields.retainAll(this.toFields.keySet());
        return new ArrayList<>(commonFields);
    }

    public List<String> getFromOnlyFieldNames() {
        Set<String> fromOnlyFields = new HashSet<>(this.fromFields.keySet());
        fromOnlyFields.removeAll(this.toFields.keySet());
        return new ArrayList<>(fromOnlyFields);
    }
    public List<String> getToOnlyFieldNames() {
        Set<String> toOnlyFields = new HashSet<>(this.toFields.keySet());
        toOnlyFields.removeAll(this.toFields.keySet());
        return new ArrayList<>(toOnlyFields);
    }


    public static TransformerMethod of(ClassModel from, ClassModel to) {
        // TODO: lower the first letter of the method name
        return new TransformerMethod(from, to, from.getName() + "2" + to.getName(), true);
    }

    public static TransformerMethod of(ClassModel from, ClassModel to, String methodName) {
        return new TransformerMethod(from, to, methodName, false);
    }
}
