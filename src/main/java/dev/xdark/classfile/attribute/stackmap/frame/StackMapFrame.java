package dev.xdark.classfile.attribute.stackmap.frame;

public abstract class StackMapFrame<SELF extends StackMapFrame<SELF>> {
    protected final int offsetDelta;

    /**
     * @param offsetDelta Offset delta.
     */
    protected StackMapFrame(int offsetDelta) {
        this.offsetDelta = offsetDelta;
    }

    /**
     * @return Offset delta.
     */
    public int getOffsetDelta() {
        return offsetDelta;
    }

    /**
     * @return Actual frame type.
     * May differ from {@link StackMapFrameType} if the type varies.
     */
    public abstract int getType();

    /**
     * @return Frame type.
     */
    public abstract StackMapFrameType<SELF> type();
}
