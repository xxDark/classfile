package dev.xdark.classfile.attribute;

import java.io.IOException;

/**
 * Thrown when known attribute contains invalid data.
 *
 * @author xDark
 */
public final class InvalidAttributeException extends IOException {

    /**
     * {@inheritDoc}
     */
    public InvalidAttributeException() {
    }

    /**
     * {@inheritDoc}
     */
    public InvalidAttributeException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public InvalidAttributeException(String message, Throwable cause) {
        super(message, cause);
    }
}
