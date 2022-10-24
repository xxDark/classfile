package dev.xdark.classfile.io;

import java.io.IOException;

/**
 * Context-aware encoder.
 *
 * @author xDark
 */
@FunctionalInterface
public interface ContextEncoder<T, CTX> {

    /**
     * @param output Output to write instance to.
     * @param value  Value to write.
     * @param ctx    Context.
     * @throws IOException If any I/O error occurs.
     */
    void write(Output output, T value, CTX ctx) throws IOException;
}
