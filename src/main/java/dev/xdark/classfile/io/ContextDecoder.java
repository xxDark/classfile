package dev.xdark.classfile.io;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Context-aware decoder.
 *
 * @author xDark
 */
@FunctionalInterface
public interface ContextDecoder<T, CTX> {

    /**
     * @param input Input to read instance from.
     * @param ctx   Context.
     * @return Read instance.
     * @throws IOException If any I/O error occurs.
     */
    @NotNull T read(@NotNull Input input, @NotNull CTX ctx) throws IOException;
}
