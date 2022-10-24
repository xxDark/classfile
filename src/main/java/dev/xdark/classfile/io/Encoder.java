package dev.xdark.classfile.io;

import java.io.IOException;

/**
 * Instance encoder.
 *
 * @author xDark
 */
@FunctionalInterface
public interface Encoder<T> {

    /**
     * @param output Output to write instance to.
     * @param value  Value to write.
     * @throws IOException If any I/O error occurs.
     */
    void write(Output output, T value) throws IOException;
}
