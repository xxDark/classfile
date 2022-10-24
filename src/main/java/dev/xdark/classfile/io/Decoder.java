package dev.xdark.classfile.io;

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
    T read(Input input) throws IOException;
}
