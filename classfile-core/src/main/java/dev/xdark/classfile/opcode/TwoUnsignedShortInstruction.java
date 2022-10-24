package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;

/**
 * Instruction with two unsigned short operands.
 *
 * @author xDark
 */
public final class TwoUnsignedShortInstruction extends AbstractInstruction<TwoUnsignedShortInstruction> {
    static final Codec<TwoUnsignedShortInstruction> CODEC = Codec.of(input -> {
        return new TwoUnsignedShortInstruction(Opcode.require(input.readUnsignedByte()), input.readUnsignedShort(), input.readUnsignedShort());
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        output.writeShort(value.getFirstOperand());
        output.writeShort(value.getSecondOperand());
    });
    private final int firstOperand, secondOperand;

    /**
     * @param opcode        Opcode.
     * @param firstOperand  First operand.
     * @param secondOperand Second operand.
     */
    public TwoUnsignedShortInstruction(Opcode<TwoUnsignedShortInstruction> opcode, int firstOperand, int secondOperand) {
        super(opcode);
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    /**
     * @return First operand.
     */
    public int getFirstOperand() {
        return firstOperand;
    }

    /**
     * @return Second operand.
     */
    public int getSecondOperand() {
        return secondOperand;
    }
}
