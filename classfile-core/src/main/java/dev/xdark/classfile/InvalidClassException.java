package dev.xdark.classfile;

import java.io.IOException;

/**
 * Thrown when class contains invalid data.
 *
 * @author xDark
 */
public class InvalidClassException extends IOException {

    public InvalidClassException() {
    }

    public InvalidClassException(String message) {
        super(message);
    }

    public InvalidClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
