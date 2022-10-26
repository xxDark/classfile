package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

/**
 * Jump instruction in which offset
 * is encoded as an unsigned int.
 *
 * @author xDark
 */
public final class IntJumpInstruction extends JumpInstruction<IntJumpInstruction> {
    static final Codec<IntJumpInstruction> CODEC = Codec.of(input -> {
        int bytecodePosition = input.position();
        return new IntJumpInstruction(Opcode.require(input), new Label(bytecodePosition + input.readInt()));
    }, (output, value) -> {
        int bytecodePosition = output.position();
        output.writeByte(value.getOpcode().opcode());
        value.getLabel().write(bytecodePosition, output, true);
    }, Skip.exact(5));

    public IntJumpInstruction(Opcode<IntJumpInstruction> opcode, Label offset) {
        super(opcode, offset);
    }
}
