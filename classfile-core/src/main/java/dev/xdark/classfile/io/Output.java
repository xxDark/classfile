package dev.xdark.classfile.io;

import java.io.DataOutput;
import java.io.IOException;

/**
 * Output interface.
 *
 * @author xDark
 */
public interface Output extends DataOutput, Seekable {

    /**
     * {@inheritDoc}
     */
    @Override
    int position();

    /**
     * @param position New position.
     * @return This output.
     */
    @Override
    Output position(int position) throws IOException;
}
