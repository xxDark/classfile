package dev.xdark.classfile;

import java.io.IOException;

/**
 * Thrown when class contains invalid data.
 *
 * @author xDark
 */
public final class InvalidClassException extends IOException {

    /**
     * {@inheritDoc}
     */
    public InvalidClassException() {
    }

    /**
     * {@inheritDoc}
     */
    public InvalidClassException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public InvalidClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
