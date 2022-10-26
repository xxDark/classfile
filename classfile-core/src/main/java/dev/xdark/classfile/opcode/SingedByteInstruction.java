package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

/**
 * Instruction with a single, signed byte operand.
 *
 * @author xDark
 */
public final class SingedByteInstruction extends AbstractInstruction<SingedByteInstruction> {
    static final Codec<SingedByteInstruction> CODEC = Codec.of(input -> {
        return new SingedByteInstruction(Opcode.require(input), input.readByte());
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        output.writeByte(value.getOperand());
    }, Skip.exact(2));
    private final int operand;

    public SingedByteInstruction(Opcode<SingedByteInstruction> opcode, int operand) {
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
