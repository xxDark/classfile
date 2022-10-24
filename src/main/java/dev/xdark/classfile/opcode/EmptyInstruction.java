package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;

/**
 * Empty instruction with no operands.
 *
 * @author xDark
 */
public final class EmptyInstruction extends AbstractInstruction<EmptyInstruction> {
    static final Codec<EmptyInstruction> CODEC = Codec.of(input -> {
        return new EmptyInstruction(Opcode.require(input.readUnsignedByte()));
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
    });

    /**
     * @param opcode Opcode.
     */
    public EmptyInstruction(Opcode<EmptyInstruction> opcode) {
        super(opcode);
    }
}
