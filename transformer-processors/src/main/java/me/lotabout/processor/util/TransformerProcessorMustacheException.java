package me.lotabout.processor.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TransformerProcessorMustacheException extends RuntimeException {
    private static final long serialVersionUID = -8025434890845392407L;

    private final String message;

    private final Throwable cause;

    public TransformerProcessorMustacheException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    public TransformerProcessorMustacheException(String message) {
        this.message = message;
        this.cause = null;
    }

    @Override
    public String getMessage() {
        // FIXME if stacktrace can be shown on jenkins log via system.out, following stack part is not needed. Though
        // this exception happens in only compilation time..
        String detail = "";
        String stack = "";
        if (this.cause != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            this.cause.printStackTrace(pw);
            pw.flush();
            stack = sw.toString();
            detail = this.cause.toString();
        }
        return this.message + " cause: " + detail + " stack: " + stack;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}
