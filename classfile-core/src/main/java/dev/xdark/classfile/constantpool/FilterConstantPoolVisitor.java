package dev.xdark.classfile.constantpool;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Constant visitor that forwards to another visitor.
 *
 * @author xDark
 */
public class FilterConstantPoolVisitor implements ConstantPoolVisitor {
    @Nullable
    protected ConstantPoolVisitor cv;

    public FilterConstantPoolVisitor(@Nullable ConstantPoolVisitor cv) {
        this.cv = cv;
    }

    public FilterConstantPoolVisitor() {
    }

    @Override
    public void visitConstants() {
        ConstantPoolVisitor cv = this.cv;
        if (cv != null) {
            cv.visitConstants();
        }
    }

    @Override
    public void visitConstant(@NotNull ConstantEntry<?> entry) {
        ConstantPoolVisitor cv = this.cv;
        if (cv != null) {
            cv.visitConstant(entry);
        }
    }

    @Override
    public void visitEnd() {
        ConstantPoolVisitor cv = this.cv;
        if (cv != null) {
            cv.visitEnd();
        }
    }
}
