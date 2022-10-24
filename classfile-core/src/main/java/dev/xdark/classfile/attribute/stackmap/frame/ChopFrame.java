package dev.xdark.classfile.attribute.stackmap.frame;

import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.io.Codec;

/**
 * Same locals as the previous frame except K locals are absent, the stack is empty.
 * Offset delta is equal to the tag.
 * K is calculated by {@code 251 - frameType}.
 *
 * @author xDark
 */
public final class ChopFrame extends StackMapFrame<ChopFrame> {
    static final Codec<ChopFrame> CODEC = Codec.of(input -> {
        int tag = input.readUnsignedByte();
        if (!FrameTypeRange.CHOP.includes(tag)) {
            throw new InvalidAttributeException("Tag mismatch");
        }
        return new ChopFrame(tag, input.readUnsignedShort());
    }, (output, value) -> {
        output.writeByte(value.getType());
        output.writeShort(value.getOffsetDelta());
    });

    private final int type;

    /**
     * @param type Frame tag.
     * @param offsetDelta Offset delta.
     */
    public ChopFrame(int type, int offsetDelta) {
        super(offsetDelta);
        this.type = type;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public StackMapFrameType<ChopFrame> type() {
        return StackMapFrameType.CHOP;
    }
}
