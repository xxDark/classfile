package dev.xdark.classfile.attribute.stackmap.frame;

import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

/**
 * Same locals as the previous frame, the stack is empty.
 * Offset delta is read from the attribute info.
 *
 * @author xDark
 */
public final class SameExtendedFrame extends StackMapFrame<SameExtendedFrame> {
    static final Codec<SameExtendedFrame> CODEC = Codec.of(input -> {
        int tag = input.readUnsignedByte();
        if (!FrameTypeRange.SAME_FRAME_EXTENDED.includes(tag)) {
            throw new InvalidAttributeException("Tag mismatch");
        }
        return new SameExtendedFrame(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeByte(value.getType());
        output.writeShort(value.getOffsetDelta());
    }, Skip.exact(3));

    /**
     * @param offsetDelta Offset delta.
     */
    public SameExtendedFrame(int offsetDelta) {
        super(offsetDelta);
    }

    @Override
    public int getType() {
        return FrameTypeRange.SAME_FRAME_EXTENDED.exact();
    }

    @Override
    public StackMapFrameType<SameExtendedFrame> type() {
        return StackMapFrameType.SAME_FRAME_EXTENDED;
    }
}
