package dev.xdark.classfile.attribute.stackmap.frame;

import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.attribute.stackmap.type.VerificationType;
import dev.xdark.classfile.attribute.stackmap.type.VerificationTypeInfo;
import dev.xdark.classfile.io.Codec;

import java.util.ArrayList;
import java.util.List;

/**
 * Full frame with explicitly set offset delta, locals and the stack.
 *
 * @author xDark
 */
public final class FullFrame extends StackMapFrame<FullFrame> {
    static final Codec<FullFrame> CODEC = Codec.of(input -> {
        int type = input.readUnsignedByte();
        if (!FrameTypeRange.FULL_FRAME.includes(type)) {
            throw new InvalidAttributeException("Tag mismatch");
        }
        int offsetDelta = input.readUnsignedShort();
        int numberOfLocals = input.readUnsignedShort();
        List<VerificationTypeInfo<?>> locals = new ArrayList<>(numberOfLocals);
        while (numberOfLocals-- != 0) {
            locals.add(VerificationType.of(input.readUnsignedByte()).codec().read(input));
        }
        int numberOfStack = input.readUnsignedShort();
        List<VerificationTypeInfo<?>> stack = new ArrayList<>(numberOfStack);
        while (numberOfStack-- != 0) {
            stack.add(VerificationType.of(input.readUnsignedByte()).codec().read(input));
        }
        return new FullFrame(offsetDelta, locals, stack);
    }, (output, value) -> {
        output.writeByte(value.getType());
        output.writeShort(value.getOffsetDelta());
        List<VerificationTypeInfo<?>> locals = value.getLocals();
        output.writeShort(locals.size());
        for (VerificationTypeInfo<?> info : locals) {
            VerificationType<?> type = info.type();
            output.writeByte(type.tag());
            ((Codec) type.codec()).write(output, info);
        }
        List<VerificationTypeInfo<?>> stack = value.getStack();
        output.writeShort(stack.size());
        for (VerificationTypeInfo<?> info : stack) {
            VerificationType<?> type = info.type();
            output.writeByte(type.tag());
            ((Codec) type.codec()).write(output, info);
        }
    });

    private final List<VerificationTypeInfo<?>> locals;
    private final List<VerificationTypeInfo<?>> stack;

    /**
     * @param offsetDelta Offset delta.
     * @param locals      Locals.
     * @param stack       Stack.
     */
    public FullFrame(int offsetDelta, List<VerificationTypeInfo<?>> locals, List<VerificationTypeInfo<?>> stack) {
        super(offsetDelta);
        this.locals = locals;
        this.stack = stack;
    }

    /**
     * @return Locals.
     */
    public List<VerificationTypeInfo<?>> getLocals() {
        return locals;
    }

    /**
     * @return Stack.
     */
    public List<VerificationTypeInfo<?>> getStack() {
        return stack;
    }

    @Override
    public int getType() {
        return FrameTypeRange.FULL_FRAME.exact();
    }

    @Override
    public StackMapFrameType<FullFrame> type() {
        return StackMapFrameType.FULL_FRAME;
    }
}
