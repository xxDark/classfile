package dev.xdark.classfile.opcode;

import dev.xdark.classfile.attribute.code.Label;
import dev.xdark.classfile.io.Codec;

/**
 * Jump instruction in which offset
 * is encoded as an unsigned int.
 *
 * @author xDark
 */
public final class IntJumpInstruction extends JumpInstruction<IntJumpInstruction> {
    static final Codec<IntJumpInstruction> CODEC = Codec.of(input -> {
        return new IntJumpInstruction(Opcode.require(input.readUnsignedByte()), Label.offset(input.readInt()));
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        output.writeInt(value.getLabel().getOffset());
    });

    public IntJumpInstruction(Opcode<IntJumpInstruction> opcode, Label offset) {
        super(opcode, offset);
    }
}
