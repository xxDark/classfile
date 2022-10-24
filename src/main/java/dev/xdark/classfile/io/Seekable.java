package dev.xdark.classfile.io;

import java.io.IOException;

/**
 * Seekable I/O.
 *
 * @author xDark
 */
public interface Seekable {

    /**
     * @return Current position.
     */
    int position();

    /**
     * @param position New position.
     * @return This instance.
     */
    Seekable position(int position) throws IOException;
}
