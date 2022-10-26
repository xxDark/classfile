package dev.xdark.classfile.attribute.stackmap.type;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

/**
 * Uninitialized_variable_info.
 *
 * @author xDark
 */
public final class UninitializedVariableVerificationTypeInfo implements VerificationTypeInfo<UninitializedVariableVerificationTypeInfo> {
    public static final Codec<UninitializedVariableVerificationTypeInfo> CODEC = Codec.of(input -> {
        return new UninitializedVariableVerificationTypeInfo(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.getInstructionOffset());
    }, Skip.exact(2));
    private final int instructionOffset;

    /**
     * @param instructionOffset Instruction offset.
     */
    public UninitializedVariableVerificationTypeInfo(int instructionOffset) {
        this.instructionOffset = instructionOffset;
    }

    /**
     * @return Instruction offset.
     */
    public int getInstructionOffset() {
        return instructionOffset;
    }

    @Override
    public VerificationType<UninitializedVariableVerificationTypeInfo> type() {
        return VerificationType.ITEM_Uninitialized;
    }
}
