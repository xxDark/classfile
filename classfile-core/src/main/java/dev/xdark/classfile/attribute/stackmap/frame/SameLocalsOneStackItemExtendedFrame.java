package dev.xdark.classfile.attribute.stackmap.frame;

import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.attribute.stackmap.type.VerificationType;
import dev.xdark.classfile.attribute.stackmap.type.VerificationTypeInfo;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

/**
 * Same locals as the previous frame, the stack has 1 value.
 * Offset delta is read from the attribute info.
 *
 * @author xDark
 */
public final class SameLocalsOneStackItemExtendedFrame extends StackMapFrame<SameLocalsOneStackItemExtendedFrame> {
    static final Codec<SameLocalsOneStackItemExtendedFrame> CODEC = Codec.of(input -> {
        int frameType = input.readUnsignedByte();
        if (frameType != FrameTypeRange.SAME_LOCALS_1_STACK_ITEM_EXTENDED.getFrom()) {
            throw new InvalidAttributeException("Mismatched frame type");
        }
        int offsetDelta = input.readUnsignedShort();
        int tag = input.readUnsignedByte();
        VerificationType<?> verificationType = VerificationType.of(tag);
        if (verificationType == null) {
            throw new InvalidAttributeException("Unknown verification type " + tag);
        }
        VerificationTypeInfo<?> info = verificationType.codec().read(input);
        return new SameLocalsOneStackItemExtendedFrame(offsetDelta, info);
    }, (output, value) -> {
        output.writeByte(value.getType());
        output.writeShort(value.getOffsetDelta());
        VerificationTypeInfo<?> stackValue = value.getStackValue();
        VerificationType<?> type = stackValue.type();
        output.writeByte(type.tag());
        ((Codec) type.codec()).write(output, stackValue);
    }, Skip.exact(3).then(input -> {
        int tag = input.readUnsignedByte();
        VerificationType<?> verificationType = VerificationType.of(tag);
        if (verificationType == null) {
            throw new InvalidAttributeException("Unknown verification type " + tag);
        }
        verificationType.codec().skip(input);
    }));

    private final VerificationTypeInfo<?> stackValue;

    /**
     * @param offsetDelta Offset delta.
     * @param stackValue  One stack value.
     */
    public SameLocalsOneStackItemExtendedFrame(int offsetDelta, VerificationTypeInfo<?> stackValue) {
        super(offsetDelta);
        this.stackValue = stackValue;
    }

    /**
     * @return Value on the stack.
     */
    public VerificationTypeInfo<?> getStackValue() {
        return stackValue;
    }

    @Override
    public int getType() {
        return FrameTypeRange.SAME_LOCALS_1_STACK_ITEM_EXTENDED.exact();
    }

    @Override
    public StackMapFrameType<SameLocalsOneStackItemExtendedFrame> type() {
        return StackMapFrameType.SAME_LOCALS_1_STACK_ITEM_EXTENDED;
    }
}
