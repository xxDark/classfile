package dev.xdark.classfile.io;

import java.io.DataInput;
import java.io.IOException;

/**
 * Input interface.
 *
 * @author xDark
 */
public interface Input extends Seekable, DataInput {

    /**
     * @param length Byte array length.
     * @return Read array.
     */
    byte[] read(int length) throws IOException;

    /**
     * {@inheritDoc}
     */
    @Override
    int position();

    /**
     * @param position New position.
     * @return This input.
     */
    @Override
    Input position(int position) throws IOException;
}
