package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.attribute.CodeAttribute;
import dev.xdark.classfile.opcode.Instruction;
import dev.xdark.classfile.opcode.Label;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Code visitor that forwards to another visitor.
 *
 * @author xDark
 */
public class FilterCodeVisitor implements CodeVisitor {
    @Nullable
    protected CodeVisitor cv;

    public FilterCodeVisitor(@Nullable CodeVisitor cv) {
        this.cv = cv;
    }

    public FilterCodeVisitor() {
    }

    @Override
    public void visitCode() {
        CodeVisitor cv = this.cv;
        if (cv != null) {
            cv.visitCode();
        }
    }

    @Override
    public @Nullable AttributeVisitor visitAttributes() {
        CodeVisitor cv = this.cv;
        return cv == null ? null : cv.visitAttributes();
    }

    @Override
    public void visitInstruction(@NotNull Instruction<?> instruction) {
        CodeVisitor cv = this.cv;
        if (cv != null) cv.visitInstruction(instruction);
    }

    @Override
    public void visitLabel(@NotNull Label label) {
        CodeVisitor cv = this.cv;
        if (cv != null) {
            cv.visitLabel(label);
        }
    }

    @Override
    public void visitTryCatchBlock(CodeAttribute.@NotNull TryCatchBlock tryCatchBlock) {
        CodeVisitor cv = this.cv;
        if (cv != null) {
            cv.visitTryCatchBlock(tryCatchBlock);
        }
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        CodeVisitor cv = this.cv;
        if (cv != null) {
            cv.visitMaxs(maxStack, maxLocals);
        }
    }

    @Override
    public void visitEnd() {
        CodeVisitor cv = this.cv;
        if (cv != null) {
            cv.visitEnd();
        }
    }
}
