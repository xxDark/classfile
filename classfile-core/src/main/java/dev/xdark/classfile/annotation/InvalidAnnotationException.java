package dev.xdark.classfile.annotation;

import dev.xdark.classfile.InvalidClassException;

/**
 * Thrown when annotation contains invalid data.
 *
 * @author xDark
 */
public final class InvalidAnnotationException extends InvalidClassException {

    public InvalidAnnotationException() {
    }

    public InvalidAnnotationException(String message) {
        super(message);
    }

    public InvalidAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }
}
