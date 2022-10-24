package dev.xdark.classfile.attribute;

import org.jetbrains.annotations.NotNull;

/**
 * Attribute.
 *
 * @author xDark
 */
public interface Attribute<SELF extends Attribute<SELF>> {

    /**
     * @return Attribute info.
     */
    @NotNull AttributeInfo<SELF> info();
}
