package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;

/**
 * Instruction with a single, signed byte operand.
 *
 * @author xDark
 */
public final class UnsignedByteInstruction extends AbstractInstruction<UnsignedByteInstruction> {
    static final Codec<UnsignedByteInstruction> CODEC = Codec.of(input -> {
        return new UnsignedByteInstruction(Opcode.require(input.readUnsignedByte()), input.readUnsignedByte());
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        output.writeByte(value.getOperand());
    });
    private final int operand;

    public UnsignedByteInstruction(Opcode<UnsignedByteInstruction> opcode, int operand) {
        super(opcode);
        this.operand = operand;
    }

    /**
     * @return Operand.
     */
    public int getOperand() {
        return operand;
    }
}
