package dev.xdark.classfile.method;

import dev.xdark.classfile.AccessFlag;
import dev.xdark.classfile.attribute.AttributeVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * Method visitor that forwards to another visitor.
 *
 * @author xDark
 */
public class FilterMethodVisitor implements MethodVisitor {
    protected MethodVisitor mv;

    public FilterMethodVisitor(MethodVisitor mv) {
        this.mv = mv;
    }

    public FilterMethodVisitor() {
    }

    @Override
    public void visit(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        MethodVisitor mv = this.mv;
        if (mv != null) {
            mv.visit(access, nameIndex, descriptorIndex);
        }
    }

    @Override
    public AttributeVisitor visitAttributes() {
        MethodVisitor mv = this.mv;
        return mv == null ? null : mv.visitAttributes();
    }

    @Override
    public void visitEnd() {
        MethodVisitor mv = this.mv;
        if (mv != null) {
            mv.visitEnd();
        }
    }
}
