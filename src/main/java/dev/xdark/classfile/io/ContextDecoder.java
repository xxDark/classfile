package dev.xdark.classfile.io;

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
    T read(Input input, CTX ctx) throws IOException;
}
