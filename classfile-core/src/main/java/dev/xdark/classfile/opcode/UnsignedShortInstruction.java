package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;

/**
 * Instruction with a single, uigned short operand.
 *
 * @author xDark
 */
public final class UnsignedShortInstruction extends AbstractInstruction<UnsignedShortInstruction> {
    static final Codec<UnsignedShortInstruction> CODEC = Codec.of(input -> {
        return new UnsignedShortInstruction(Opcode.require(input), input.readUnsignedShort());
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        output.writeShort(value.getOperand());
    });
    private final int operand;

    public UnsignedShortInstruction(Opcode<UnsignedShortInstruction> opcode, int operand) {
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
