package dev.xdark.classfile.file;

import dev.xdark.classfile.AccessFlag;
import dev.xdark.classfile.attribute.AttributeVisitor;

/**
 * Field visitor.
 *
 * @author xDark
 */
public interface FieldVisitor {

    /**
     * @param access          Access flag.
     * @param nameIndex       Name index.
     * @param descriptorIndex Descriptor index.
     */
    void visit(AccessFlag access, int nameIndex, int descriptorIndex);

    /**
     * @return New attribute visitor or {@code null},
     * if attributes should not be visited.
     */
    AttributeVisitor visitAttributes();

    /**
     * Called after method has been visited.
     */
    void visitEnd();
}
