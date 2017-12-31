package me.lotabout.processor.model;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public class EntryFactory {
    private int id;


    public static TypeEntry of(TypeMirror type) {
        switch (type.getKind()) {
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case SHORT:
            case INT:
            case LONG:
            case FLOAT:
            case DOUBLE:
                return new PrimitiveEntry(type);
            case ARRAY:
                return new ArrayEntry(type);
            case DECLARED:
                TypeElement element = (TypeElement)((DeclaredType)type).asElement();
                switch (element.getQualifiedName().toString()) {
                    case "java.util.Iterable":
                    case "java.util.Collection":
                    case "java.util.List":
                    case "java.util.Queue":
                    case "java.util.Set":
                    case "java.util.Deque":
                    case "java.util.SortedSet":
                    case "java.util.ArrayList":
                    case "java.util.LinkedList":
                    case "java.util.Vector":
                    case "java.util.Stack":
                    case "java.util.PriorityQueue":
                    case "java.util.ArrayDeque":
                    case "java.util.HashSet":
                    case "java.util.LinkedHashSet":
                    case "java.util.TreeSet":
                        return new CollectionClassEntry(type);
                    case "java.util.Map":
                    case "java.util.HashMap":
                    case "java.util.LinkedHashMap":
                    case "java.util.TreeMap":
                        return new MapClassEntry(type);
                    default:
                        return new ConcreteClassEntry(type);
                }
            default:
                return null;
        }
    }

    public static FieldEntry of(VariableElement element) {
        return new FieldEntryImpl(element);
    }

    public static MethodEntry of(ExecutableElement element) {
        return new MethodEntryImpl(element);
    }
}
