package dev.xdark.classfile.io;

import org.jetbrains.annotations.NotNull;

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
    void write(@NotNull Output output, @NotNull T value) throws IOException;
}
