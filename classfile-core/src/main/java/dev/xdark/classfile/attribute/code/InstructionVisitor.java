package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.opcode.Instruction;
import org.jetbrains.annotations.NotNull;

/**
 * Code visitor.
 *
 * @author xDark
 */
public interface InstructionVisitor {

    /**
     * Called before instructions are visited.
     */
    void visitInstructions();

    /**
     * @param instruction Instruction to visit.
     */
    void visitInstruction(@NotNull Instruction<?> instruction);

    /**
     * Called after all instructions have been visited.
     */
    void visitEnd();
}
