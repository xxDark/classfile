package dev.xdark.classfile.attribute;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Attribute visitor that forwards to another visitor.
 *
 * @author xDark
 */
public abstract class FilterAttributeVisitor implements AttributeVisitor {
    @Nullable
    protected AttributeVisitor av;

    protected FilterAttributeVisitor(@Nullable AttributeVisitor av) {
        this.av = av;
    }

    protected FilterAttributeVisitor() {
    }

    @Override
    public void visitAttributes() {
        AttributeVisitor av = this.av;
        if (av != null) {
            av.visitAttributes();
        }
    }

    @Override
    public void visitAttribute(int nameIndex, @NotNull Attribute<?> attribute) {
        AttributeVisitor av = this.av;
        if (av != null) {
            av.visitAttribute(nameIndex, attribute);
        }
    }

    @Override
    public void visitEnd() {
        AttributeVisitor av = this.av;
        if (av != null) {
            av.visitEnd();
        }
    }
}
