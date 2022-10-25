package dev.xdark.classfile.annotation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Annotation array visitor.
 *
 * @author xDark
 */
public interface AnnotationArrayVisitor {

    /**
     * Called before array is visited.
     *
     * @param length Array length.
     */
    void visitArray(int length);

    /**
     * @param value Element constant.
     */
    void visitConstant(@NotNull ElementValueConstant<?> value);

    /**
     * @param value Enum element.
     */
    void visitEnum(@NotNull ElementValueEnum value);

    /**
     * @return Nested annotation visitor or {@code null},
     * if annotation can be skipped.
     */
    @Nullable AnnotationVisitor visitAnnotation();

    /**
     * Called after the array has been visited.
     */
    void visitEnd();
}
