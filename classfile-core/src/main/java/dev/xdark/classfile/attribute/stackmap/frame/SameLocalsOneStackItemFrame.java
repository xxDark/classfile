package dev.xdark.classfile.attribute.stackmap.frame;

import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.attribute.stackmap.type.VerificationType;
import dev.xdark.classfile.attribute.stackmap.type.VerificationTypeInfo;
import dev.xdark.classfile.io.Codec;

/**
 * Same locals as the previous frame, the stack has 1 value.
 * Offset delta is equal to {@code 64 - tag}.
 *
 * @author xDark
 */
public final class SameLocalsOneStackItemFrame extends StackMapFrame<SameLocalsOneStackItemFrame> {
    static final Codec<SameLocalsOneStackItemFrame> CODEC = Codec.of(input -> {
        int frameType = input.readUnsignedByte();
        int tag = input.readUnsignedByte();
        VerificationType<?> verificationType = VerificationType.of(tag);
        if (verificationType == null) {
            throw new InvalidAttributeException("Unknown verification type " + tag);
        }
        VerificationTypeInfo<?> info = verificationType.codec().read(input);
        return new SameLocalsOneStackItemFrame(frameType - FrameTypeRange.SAME_LOCALS_1_STACK_ITEM.getFrom(), info);
    }, (output, value) -> {
        output.writeByte(value.getType());
        VerificationTypeInfo<?> stackValue = value.getStackValue();
        VerificationType<?> type = stackValue.type();
        output.writeByte(type.tag());
        ((Codec) type.codec()).write(output, stackValue);
    });

    private final VerificationTypeInfo<?> stackValue;

    /**
     * @param offsetDelta Offset delta.
     * @param stackValue  One stack value.
     */
    public SameLocalsOneStackItemFrame(int offsetDelta, VerificationTypeInfo<?> stackValue) {
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
        return offsetDelta + FrameTypeRange.SAME_LOCALS_1_STACK_ITEM.getFrom();
    }

    @Override
    public StackMapFrameType<SameLocalsOneStackItemFrame> type() {
        return StackMapFrameType.SAME_LOCALS_1_STACK_ITEM;
    }
}
