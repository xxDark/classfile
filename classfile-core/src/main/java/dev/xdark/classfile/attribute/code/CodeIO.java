package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.attribute.CodeAttribute;
import dev.xdark.classfile.io.Input;
import dev.xdark.classfile.opcode.Instruction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Code IO.
 *
 * @author xDark
 */
public final class CodeIO {
    private CodeIO() {
    }

    /**
     * Visits Code attribute.
     *
     * @param code    Attribute to visit.
     * @param visitor Attribute visitor.
     */
    public static void read(CodeAttribute code, CodeVisitor visitor) throws IOException {
        visitor.visitCode();
        InstructionIO.read(Input.wrap(code.getCode()), new FilterInstructionVisitor() {
            @Override
            public void visitInstruction(@NotNull Instruction<?> instruction) {
                visitor.visitInstruction(instruction);
            }
        });
        code.getTryCatchBlocks().forEach(visitor::visitTryCatchBlock);
        visitor.visitMaxs(code.getMaxStack(), code.getMaxLocals());
        visitor.visitEnd();
    }
}
