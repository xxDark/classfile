package dev.xdark.classfile.attribute.code;

/**
 * Label.
 *
 * @author xDArk
 */
public final class Label {
    private int position;
    private int offset;

    private Label(int position, int offset) {
        this.position = position;
        this.offset = offset;
    }

    public Label() {
        this(-1, -1);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public static Label exact(int position) {
        return new Label(position, -1);
    }

    public static Label offset(int offset) {
        return new Label(-1, offset);
    }

    public static Label create(int position, int offset) {
        return new Label(position, offset);
    }
}
