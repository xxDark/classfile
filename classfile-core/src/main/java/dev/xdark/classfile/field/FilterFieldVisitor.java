package dev.xdark.classfile.field;

import dev.xdark.classfile.AccessFlag;
import dev.xdark.classfile.attribute.AttributeVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Field visitor that forwards to another visitor.
 *
 * @author xDark
 */
public class FilterFieldVisitor implements FieldVisitor {
    @Nullable
    protected FieldVisitor fv;

    public FilterFieldVisitor(@Nullable FieldVisitor fv) {
        this.fv = fv;
    }

    public FilterFieldVisitor() {
    }

    @Override
    public void visit(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        FieldVisitor mv = this.fv;
        if (mv != null) {
            mv.visit(access, nameIndex, descriptorIndex);
        }
    }

    @Override
    public AttributeVisitor visitAttributes() {
        FieldVisitor mv = this.fv;
        return mv == null ? null : mv.visitAttributes();
    }

    @Override
    public void visitEnd() {
        FieldVisitor mv = this.fv;
        if (mv != null) {
            mv.visitEnd();
        }
    }
}
