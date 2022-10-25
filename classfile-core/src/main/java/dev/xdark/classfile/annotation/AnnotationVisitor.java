package dev.xdark.classfile.annotation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Annotation visitor.
 *
 * @author xDark
 */
public interface AnnotationVisitor {

    /**
     * @param typeIndex Annotation class index.
     * @param count     Element count.
     */
    void visitAnnotation(int typeIndex, int count);

    /**
     * @param nameIndex Name index.
     * @param value     Element constant.
     */
    void visitConstant(int nameIndex, @NotNull ElementValueConstant<?> value);

    /**
     * @param nameIndex Name index.
     * @param value     Enum element.
     */
    void visitEnum(int nameIndex, @NotNull ElementValueEnum value);

    /**
     * @param nameIndex Name index.
     * @return Nested annotation visitor or {@code null},
     * if annotation can be skipped.
     */
    @Nullable AnnotationVisitor visitNestedAnnotation(int nameIndex);

    /**
     * @param nameIndex Name index.
     * @return Array visitor or {@code null},
     * if annotation can be skipped.
     */
    @Nullable AnnotationArrayVisitor visitArray(int nameIndex);

    /**
     * Called after annotation has been visited.
     */
    void visitEnd();
}
