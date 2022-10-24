package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.opcode.Instruction;
import org.jetbrains.annotations.NotNull;

/**
 * Instruction visitor that forwards to another visitor.
 *
 * @author xDark
 */
public class FilterInstructionVisitor implements InstructionVisitor {
    protected InstructionVisitor cv;

    public FilterInstructionVisitor(InstructionVisitor cv) {
        this.cv = cv;
    }

    public FilterInstructionVisitor() {
    }

    @Override
    public void visitInstructions() {
        InstructionVisitor cv = this.cv;
        if (cv != null) {
            cv.visitInstructions();
        }
    }

    @Override
    public void visitInstruction(@NotNull Instruction<?> instruction) {
        InstructionVisitor cv = this.cv;
        if (cv != null) {
            cv.visitInstruction(instruction);
        }
    }

    @Override
    public void visitEnd() {
        InstructionVisitor cv = this.cv;
        if (cv != null) {
            cv.visitEnd();
        }
    }
}
