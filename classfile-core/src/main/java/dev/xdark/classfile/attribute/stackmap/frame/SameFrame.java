package dev.xdark.classfile.attribute.stackmap.frame;

import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.io.Codec;

/**
 * Same locals as the previous frame, the stack is empty.
 * Offset delta is equal to the tag.
 *
 * @author xDark
 */
public final class SameFrame extends StackMapFrame<SameFrame> {
    static final Codec<SameFrame> CODEC = Codec.of(input -> {
        int tag = input.readUnsignedByte();
        if (!FrameTypeRange.SAME.includes(tag)) {
            throw new InvalidAttributeException("Tag mismatch");
        }
        return new SameFrame(tag);
    }, (output, value) -> {
        output.writeByte(value.getType());
    });

    /**
     * @param offsetDelta Offset delta.
     */
    public SameFrame(int offsetDelta) {
        super(offsetDelta);
    }

    @Override
    public int getType() {
        return offsetDelta;
    }

    @Override
    public StackMapFrameType<SameFrame> type() {
        return StackMapFrameType.SAME;
    }
}
