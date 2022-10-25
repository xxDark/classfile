package dev.xdark.classfile.io;

import dev.xdark.classfile.io.buffer.ByteBufferInput;

import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteBuffer;

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
     * @return How many bytes do we have left?
     */
    int remaining();

    /**
     * @return Whether the EOF was reached.
     */
    boolean hasRemaining();

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

    static Input wrap(byte[] array, int off, int len) {
        return new ByteBufferInput(ByteBuffer.wrap(array, off, len));
    }

    static Input wrap(byte[] array) {
        return new ByteBufferInput(ByteBuffer.wrap(array));
    }
}
