package dev.xdark.classfile.constantpool;

import org.jetbrains.annotations.NotNull;

/**
 * Constant pool visitor.
 *
 * @author xDark
 */
public interface ConstantPoolVisitor {

    /**
     * Called before constants are visited.
     */
    void visitConstants();

    /**
     * @param entry Constant entry.
     */
    void visitConstant(@NotNull ConstantEntry<?> entry);

    /**
     * Called after all constants have been visited.
     */
    void visitEnd();
}
