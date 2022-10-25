package dev.xdark.classfile.annotation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Annotation array visitor that forwards to another visitor.
 *
 * @author xDark
 */
public class FilterAnnotationArrayVisitor implements AnnotationArrayVisitor {
    @Nullable
    protected AnnotationArrayVisitor av;

    public FilterAnnotationArrayVisitor(@Nullable AnnotationArrayVisitor av) {
        this.av = av;
    }

    @Override
    public void visitArray(int length) {
        AnnotationArrayVisitor av = this.av;
        if (av != null) {
            av.visitArray(length);
        }
    }

    @Override
    public void visitConstant(@NotNull ElementValueConstant<?> value) {
        AnnotationArrayVisitor av = this.av;
        if (av != null) {
            av.visitConstant(value);
        }
    }

    @Override
    public void visitEnum(@NotNull ElementValueEnum value) {
        AnnotationArrayVisitor av = this.av;
        if (av != null) {
            av.visitEnum(value);
        }
    }

    @Override
    public @Nullable AnnotationVisitor visitAnnotation() {
        AnnotationArrayVisitor av = this.av;
        return av == null ? null : av.visitAnnotation();
    }

    @Override
    public void visitEnd() {
        AnnotationArrayVisitor av = this.av;
        if (av != null) {
            av.visitEnd();
        }
    }
}
