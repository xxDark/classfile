package dev.xdark.classfile.attribute;

import dev.xdark.classfile.constantpool.ConstantPoolBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Attribute adapter.
 *
 * @author xDark
 */
public class AttributeAdapter extends FilterAttributeVisitor {
    protected final ConstantPoolBuilder builder;

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

    /**
     * @param attribute Attribute.
     * @throws IllegalArgumentException If attribute is not known.
     */
    public void visitAttribute(Attribute<?> attribute) {
        KnownInfo known = attribute.info().known();
        if (known == null) {
            throw new IllegalArgumentException("Unknown attribute " + attribute);
        }
        visitAttribute(known.name(), attribute);
    }
}
