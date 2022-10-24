package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;

/**
 * Jump instruction in which offset
 * is encoded as an unsigned int.
 *
 * @author xDark
 */
public final class IntJumpInstruction extends JumpInstruction<IntJumpInstruction> {
    static final Codec<IntJumpInstruction> CODEC = Codec.of(input -> {
        return new IntJumpInstruction(Opcode.require(input.readUnsignedByte()), input.readInt());
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        output.writeInt(value.getOffset());
    });

    public IntJumpInstruction(Opcode<IntJumpInstruction> opcode, int offset) {
        super(opcode, offset);
    }
}
