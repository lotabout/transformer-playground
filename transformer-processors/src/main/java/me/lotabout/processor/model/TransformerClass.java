package me.lotabout.processor.model;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.List;

public class TransformerClass {
    ProcessingEnvironment environment;
    List<TransformerMethod> transformerMethods = new ArrayList<>();

    public void addTransformerMethod(TransformerMethod method) {
        this.transformerMethods.add(method);

    }
    public void addTransformerMethods(List<TransformerMethod> methods) {
        this.transformerMethods.addAll(methods);
    }

    public void debug(Element e, String message, Object... objects) {
        final Messager messager = environment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(message, objects), e);
    }

    public void error(Element e, String message, Object... objects) {
        final Messager messager = environment.getMessager();
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(message, objects), e);
    }

    public JavaFile generate() {
        return null;
    }
}
