package me.lotabout.processor.model;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

public class ClassModel {
    private TypeElement self;

    public ClassModel(TypeElement self) {
        this.self = self;
    }

    public String getQualifiedName() {
        return self.getQualifiedName().toString();
    }

    public String getName() {
        return self.getSimpleName().toString();
    }

    public List<FieldModel> getAllFields() {
        return ElementFilter.fieldsIn(self.getEnclosedElements())
                .stream()
                .map(FieldModel::of)
                .collect(Collectors.toList());
    }

    public List<MethodModel> getAllMethods() {
        return ElementFilter.methodsIn(self.getEnclosedElements())
                .stream()
                .map(MethodModel::of)
                .collect(Collectors.toList());
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotation) {
        return self.getAnnotation(annotation);
    }

    public String getPackageName() {
        final PackageElement packageElement = (PackageElement)self.getEnclosingElement();
        return packageElement.isUnnamed() ? "" : packageElement.getQualifiedName().toString();
    }

    public TypeElement getRaw() {
        return self;
    }

    public static ClassModel of(TypeElement element) {
        return new ClassModel(element);
    }

    public boolean isCollection() {
        switch (self.getQualifiedName().toString()) {
        case "java.util.Iterable":
        case "java.util.Collection":
        case "java.util.List":
        case "java.util.Queue":
        case "java.util.Set":
        case "java.util.Deque":
        case "java.util.SortedSet":
        case "java.util.Map":
        case "java.util.ArrayList":
        case "java.util.LinkedList":
        case "java.util.Vector":
        case "java.util.Stack":
        case "java.util.PriorityQueue":
        case "java.util.ArrayDeque":
        case "java.util.HashSet":
        case "java.util.LinkedHashSet":
        case "java.util.TreeSet":
        case "java.util.HashMap":
        case "java.util.LinkedHashMap":
        case "java.util.TreeMap":
            return true;
        default:
            return false;
        }
    }
}
