package dev.xdark.classfile.file;

import dev.xdark.classfile.AccessFlag;
import dev.xdark.classfile.attribute.AttributeVisitor;

/**
 * Field visitor that forwards to another visitor.
 *
 * @author xDark
 */
public class FilterFieldVisitor implements FieldVisitor {
    protected FieldVisitor fv;

    public FilterFieldVisitor(FieldVisitor fv) {
        this.fv = fv;
    }

    public FilterFieldVisitor() {
    }

    @Override
    public void visit(AccessFlag access, int nameIndex, int descriptorIndex) {
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
