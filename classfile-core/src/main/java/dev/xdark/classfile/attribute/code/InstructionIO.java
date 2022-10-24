package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Input;
import dev.xdark.classfile.io.Output;
import dev.xdark.classfile.opcode.Instruction;
import dev.xdark.classfile.opcode.Opcode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * Instruction IO.
 *
 * @author xDark
 */
public final class InstructionIO {
    private InstructionIO() {
    }

    /**
     * Reads code instructions.
     *
     * @param input   Input to read instructions from.
     * @param visitor Instructions visitor.
     * @throws IOException If any I/O error occurs.
     */
    public static void read(@NotNull Input input, @NotNull InstructionVisitor visitor) throws IOException {
        visitor.visitInstructions();
        while (input.hasRemaining()) {
            int position = input.position();
            int n = input.readUnsignedByte();
            Opcode<?> opcode = Opcode.of(n);
            if (opcode == null) {
                throw new InvalidInstructionException("Unknown opcode " + n);
            }
            input.position(position);
            visitor.visitInstruction(opcode.codec().read(input));
        }
        visitor.visitEnd();
    }

    /**
     * Writes instructions.
     *
     * @param output       Output to write instructions to.
     * @param instructions Instructions to write.
     * @throws IOException If any I/O error occurs.
     */
    public static void write(@NotNull Output output, List<Instruction<?>> instructions) throws IOException {
        for (int i = 0, j = instructions.size(); i < j; i++) {
            Instruction<?> insn = instructions.get(i);
            ((Codec) insn.getOpcode().codec()).write(output, insn);
        }
    }
}
