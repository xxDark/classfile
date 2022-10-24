package dev.xdark.classfile.attribute;

/**
 * Attribute.
 *
 * @author xDark
 */
public interface Attribute<SELF extends Attribute<SELF>> {

    /**
     * @return Attribute info.
     */
    AttributeInfo<SELF> info();
}
