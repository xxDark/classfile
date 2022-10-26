package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

/**
 * Instruction with a single, signed short operand.
 *
 * @author xDark
 */
public final class SignedShortInstruction extends AbstractInstruction<SignedShortInstruction> {
    static final Codec<SignedShortInstruction> CODEC = Codec.of(input -> {
        return new SignedShortInstruction(Opcode.require(input), input.readShort());
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        output.writeShort(value.getOperand());
    }, Skip.exact(3));
    private final int operand;

    public SignedShortInstruction(Opcode<SignedShortInstruction> opcode, int operand) {
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
