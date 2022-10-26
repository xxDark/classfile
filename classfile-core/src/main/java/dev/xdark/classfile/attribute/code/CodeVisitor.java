package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.attribute.CodeAttribute;
import dev.xdark.classfile.opcode.Instruction;
import dev.xdark.classfile.opcode.Label;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Code visitor.
 *
 * @author xDark
 */
public interface CodeVisitor {

    /**
     * Called before code is visited.
     */
    void visitCode();

    /**
     * @return Attribute visitor or {@code null},
     * if attributes should be skipped.
     */
    @Nullable AttributeVisitor visitAttributes();

    /**
     * Visits an instruction.
     *
     * @param instruction Instruction to visit.
     */
    void visitInstruction(@NotNull Instruction<?> instruction);

    /**
     * Visits a label.
     *
     * @param label Label to visit.
     */
    void visitLabel(@NotNull Label label);

    /**
     * Visits try/catch block.
     *
     * @param tryCatchBlock Try/catch block.
     */
    void visitTryCatchBlock(@NotNull CodeAttribute.TryCatchBlock tryCatchBlock);

    /**
     * @param maxStack  Max stack.
     * @param maxLocals Max locals.
     */
    void visitMaxs(int maxStack, int maxLocals);

    /**
     * Called after code attribute has been visited.
     */
    void visitEnd();
}
