package dev.xdark.classfile.constantpool;

import org.jetbrains.annotations.NotNull;

/**
 * Constant pool entry.
 *
 * @author xDark
 */
public interface ConstantEntry<SELF extends ConstantEntry<SELF>> {

    /**
     * @return Entry tag.
     */
    @NotNull
    Tag<SELF> tag();
}
