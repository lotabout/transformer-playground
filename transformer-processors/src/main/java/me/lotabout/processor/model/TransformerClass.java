package me.lotabout.processor.model;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.List;

public class TransformerClass {
    TypeEntry originalClass;
    ProcessingEnvironment environment;
    List<TransformerMethod> transformerMethods = new ArrayList<>();

    public TransformerClass(TypeEntry originalClass, ProcessingEnvironment environment) {
        this.originalClass = originalClass;
        this.environment = environment;
    }

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
        TypeSpec.Builder builder = TypeSpec.classBuilder(originalClass.getName() + "Transformer")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        transformerMethods.forEach(method -> builder.addMethod(method.generate(this)));
        return JavaFile.builder(originalClass.getPackageName(), builder.build()).build();
    }
}
