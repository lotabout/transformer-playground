package me.lotabout.processor;

import me.lotabout.annotation.Transformer;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("me.lotabout.annotation.Transformer")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class TransformerProcessor extends AbstractProcessor {
    public TransformerProcessor() {
        super();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element: roundEnv.getElementsAnnotatedWith(Transformer.class)) {
            Transformer transformer = element.getAnnotation(Transformer.class);
            debug(element, "--->>> " + element.getSimpleName());
//            // check if a class has been annotated with @Transformer
//            if (element.getKind() != ElementKind.CLASS) {
//                error(element, "Only class can be annotated with @%s", Transformer.class.getSimpleName());
//                return true;
//            }
//
//            TypeElement fooClass = (TypeElement) element;
//
//            // get annotation
//            Transformer transformer = fooClass.getAnnotation(Transformer.class);
//            if (transformer.from().length <= 0) {
//                error(element, "Must specify at least one class to be transformed from");
//            }
//
//            Class from = transformer.from()[0];
//
//            // generate code for transformer
        }
        return true;
    }

    private void error(Element element, String msg, Object... args) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), element);
    }

    private void debug(Element element, String msg, Object... args) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, String.format(msg, args), element);
    }
}
