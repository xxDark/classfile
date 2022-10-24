package dev.xdark.classfile.attribute;

import org.jetbrains.annotations.NotNull;

/**
 * Attribute visitor.
 *
 * @author xDark
 */
public interface AttributeVisitor {

    /**
     * Called before the attributes are visited.
     */
    void visitAttributes();

    /**
     * Visits new attribute.
     *
     * @param nameIndex Attribute name index.
     * @param attribute Attribute visited.
     */
    void visitAttribute(int nameIndex, @NotNull Attribute<?> attribute);

    /**
     * Called after all attributes have been visited.
     */
    void visitEnd();
}
