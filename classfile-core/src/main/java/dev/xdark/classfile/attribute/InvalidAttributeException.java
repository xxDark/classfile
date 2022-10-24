package dev.xdark.classfile.attribute;

import dev.xdark.classfile.InvalidClassException;

/**
 * Thrown when known attribute contains invalid data.
 *
 * @author xDark
 */
public final class InvalidAttributeException extends InvalidClassException {

    public InvalidAttributeException() {
    }

    public InvalidAttributeException(String message) {
        super(message);
    }

    public InvalidAttributeException(String message, Throwable cause) {
        super(message, cause);
    }
}
