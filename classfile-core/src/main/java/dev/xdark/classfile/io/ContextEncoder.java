package dev.xdark.classfile.io;

import org.jetbrains.annotations.NotNull;

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
    void write(@NotNull Output output, @NotNull T value, @NotNull CTX ctx) throws IOException;
}
