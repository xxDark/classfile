package dev.xdark.classfile.attribute;

import dev.xdark.classfile.constantpool.ConstantPoolBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Attribute adapter.
 *
 * @author xDark
 */
public class AttributeAdapter extends FilterAttributeVisitor {
    private final ConstantPoolBuilder builder;

    /**
     * @param av      Backing attribute visitor.
     * @param builder Constant pool builder.
     */
    public AttributeAdapter(@NotNull AttributeVisitor av, ConstantPoolBuilder builder) {
        super(av);
        this.builder = builder;
    }

    /**
     * @param name      Attribute name.
     * @param attribute Attribute.
     */
    public void visitAttribute(String name, Attribute<?> attribute) {
        visitAttribute(builder.putUtf8(name), attribute);
    }
}
