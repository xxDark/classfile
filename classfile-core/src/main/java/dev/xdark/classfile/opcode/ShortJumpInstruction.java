package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;

/**
 * Jump instruction in which offset
 * is encoded as an unsigned short.
 *
 * @author xDark
 */
public final class ShortJumpInstruction extends JumpInstruction<ShortJumpInstruction> {
    static final Codec<ShortJumpInstruction> CODEC = Codec.of(input -> {
        int bytecodePosition = input.position();
        return new ShortJumpInstruction(Opcode.require(input), new Label(bytecodePosition + input.readShort()));
    }, (output, value) -> {
        int bytecodePosition = output.position();
        output.writeByte(value.getOpcode().opcode());
        value.getLabel().write(bytecodePosition, output, false);
    });

    public ShortJumpInstruction(Opcode<ShortJumpInstruction> opcode, Label offset) {
        super(opcode, offset);
    }
}
