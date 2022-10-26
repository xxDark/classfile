package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Output;

import java.io.IOException;

/**
 * Label.
 *
 * @author xDArk
 */
public final class Label {
    private int position;

    public Label(int position) {
        this.position = position;
    }

    public Label() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    void write(int bytecodePosition, Output output, boolean large) throws IOException {
        int offset = -(bytecodePosition - position);
        if (large) {
            output.writeInt(offset);
        } else {
            output.writeShort(offset);
        }
    }
}
