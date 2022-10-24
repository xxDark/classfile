package dev.xdark.classfile.io;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Instance decoder.
 *
 * @author xDark
 */
@FunctionalInterface
public interface Decoder<T> {

    /**
     * @param input Input to read instance from.
     * @return Read instance.
     * @throws IOException If any I/O error occurs.
     */
    @NotNull T read(@NotNull Input input) throws IOException;
}
