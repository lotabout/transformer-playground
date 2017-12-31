package me.lotabout.processor.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

public abstract class AbstractClassEntry implements TypeEntry {
    private TypeMirror raw;
    private TypeElement self;

    public AbstractClassEntry(TypeMirror raw) {
        this.raw = raw;
        this.self = (TypeElement)((DeclaredType)raw).asElement();
    }

    @Override
    public String getQualifiedName() {
        return self.getQualifiedName().toString();
    }

    @Override
    public String getName() {
        return self.getSimpleName().toString();
    }

    @Override
    public String getFullName() {
        return this.getName();
    }

    @Override
    public String getPackageName() {
        final PackageElement packageElement = (PackageElement)self.getEnclosingElement();
        return packageElement.isUnnamed() ? "" : packageElement.getQualifiedName().toString();
    }
    @Override
    public TypeMirror getRaw() {
        return raw;
    }

    @Override
    public List<FieldEntry> getAllFields() {
        return ElementFilter.fieldsIn(self.getEnclosedElements())
                .stream()
                .map(EntryFactory::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<MethodEntry> getAllMethods() {
        return ElementFilter.methodsIn(self.getEnclosedElements())
                .stream()
                .map(EntryFactory::of)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isBoolean() {
        return self.getQualifiedName().equals("java.lang.Boolean");
    }


    @Override
    public Optional<AnnotationMirror> getAnnotationMirror(Class<?> clazz) {
        String clazzName = clazz.getName();
        for(AnnotationMirror m : self.getAnnotationMirrors()) {
            if(m.getAnnotationType().toString().equals(clazzName)) {
                return Optional.ofNullable(m);
            }
        }
        return Optional.empty();
    }
}
