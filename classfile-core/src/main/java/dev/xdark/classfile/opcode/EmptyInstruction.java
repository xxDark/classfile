package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

/**
 * Empty instruction with no operands.
 *
 * @author xDark
 */
public final class EmptyInstruction extends AbstractInstruction<EmptyInstruction> {
    static final Codec<EmptyInstruction> CODEC = Codec.of(input -> {
        return new EmptyInstruction(Opcode.require(input));
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
    }, Skip.exact(1));

    /**
     * @param opcode Opcode.
     */
    public EmptyInstruction(Opcode<EmptyInstruction> opcode) {
        super(opcode);
    }
}
