package dev.xdark.classfile.attribute;

/**
 * Named attribute.
 *
 * @author xDark
 */
public final class NamedAttributeInstance<T extends Attribute<T>> {
    private final int nameIndex;
    private final T attribute;

    /**
     * @param nameIndex Name index.
     * @param attribute Attribute.
     */
    public NamedAttributeInstance(int nameIndex, T attribute) {
        this.nameIndex = nameIndex;
        this.attribute = attribute;
    }

    /**
     * @return Name index.
     */
    public int getNameIndex() {
        return nameIndex;
    }

    /**
     * @return Attribute.
     */
    public T getAttribute() {
        return attribute;
    }
}
