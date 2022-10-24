package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.InvalidClassException;

/**
 * Thrown when instruction contains invalid data.
 *
 * @author xDark
 */
public final class InvalidInstructionException extends InvalidClassException {

    public InvalidInstructionException() {
    }

    public InvalidInstructionException(String message) {
        super(message);
    }

    public InvalidInstructionException(String message, Throwable cause) {
        super(message, cause);
    }
}
