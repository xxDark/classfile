package dev.xdark.classfile.opcode;

import dev.xdark.classfile.attribute.code.Label;
import dev.xdark.classfile.io.Codec;

/**
 * Jump instruction in which offset
 * is encoded as an unsigned short.
 *
 * @author xDark
 */
public final class ShortJumpInstruction extends JumpInstruction<ShortJumpInstruction> {
    static final Codec<ShortJumpInstruction> CODEC = Codec.of(input -> {
        return new ShortJumpInstruction(Opcode.require(input.readUnsignedByte()), Label.offset(input.readUnsignedShort()));
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        output.writeShort(value.getLabel().getOffset());
    });

    public ShortJumpInstruction(Opcode<ShortJumpInstruction> opcode, Label offset) {
        super(opcode, offset);
    }
}
