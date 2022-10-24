package dev.xdark.classfile.attribute;

/**
 * Attribute visitor that forwards to another visitor.
 *
 * @author xDark
 */
public abstract class FilterAttributeVisitor implements AttributeVisitor {

    protected AttributeVisitor av;

    protected FilterAttributeVisitor(AttributeVisitor av) {
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
    public void visitAttribute(int nameIndex, Attribute<?> attribute) {
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
