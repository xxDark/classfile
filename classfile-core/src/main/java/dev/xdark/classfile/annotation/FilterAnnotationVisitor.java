package dev.xdark.classfile.annotation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Annotation visitor that forwards to another visitor.
 *
 * @author xDark
 */
public class FilterAnnotationVisitor implements AnnotationVisitor {
    @Nullable
    protected AnnotationVisitor av;

    public FilterAnnotationVisitor(@Nullable AnnotationVisitor av) {
        this.av = av;
    }

    @Override
    public void visitAnnotation(int typeIndex, int count) {
        AnnotationVisitor av = this.av;
        if (av != null) {
            av.visitAnnotation(typeIndex, count);
        }
    }

    @Override
    public void visitConstant(int nameIndex, @NotNull ElementValueConstant<?> value) {
        AnnotationVisitor av = this.av;
        if (av != null) {
            av.visitConstant(nameIndex, value);
        }
    }

    @Override
    public void visitEnum(int nameIndex, @NotNull ElementValueEnum value) {
        AnnotationVisitor av = this.av;
        if (av != null) {
            av.visitEnum(nameIndex, value);
        }
    }

    @Override
    public @Nullable AnnotationVisitor visitNestedAnnotation(int nameIndex) {
        AnnotationVisitor av = this.av;
        return av == null ? null : av.visitNestedAnnotation(nameIndex);
    }

    @Override
    public @Nullable AnnotationArrayVisitor visitArray(int nameIndex) {
        AnnotationVisitor av = this.av;
        return av == null ? null : av.visitArray(nameIndex);
    }

    @Override
    public void visitEnd() {
        AnnotationVisitor av = this.av;
        if (av != null) {
            av.visitEnd();
        }
    }
}
