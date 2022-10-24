package dev.xdark.classfile.attribute;

import java.util.Collection;

/**
 * Collects attributes into collection.
 *
 * @author xDark
 */
public final class AttributeCollector implements AttributeVisitor {

    private final Collection<NamedAttributeInstance<?>> attributes;

    /**
     * @param attributes Output collection.
     */
    public AttributeCollector(Collection<NamedAttributeInstance<?>> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void visitAttributes() {
    }

    @Override
    public void visitAttribute(int nameIndex, Attribute<?> attribute) {
        attributes.add(new NamedAttributeInstance(nameIndex, attribute));
    }

    @Override
    public void visitEnd() {
    }
}
