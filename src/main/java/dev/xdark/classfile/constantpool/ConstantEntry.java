package dev.xdark.classfile.constantpool;

/**
 * Constant pool entry.
 *
 * @author xDark
 */
public interface ConstantEntry<SELF extends ConstantEntry<SELF>> {

    /**
     * @return Entry tag.
     */
    Tag<SELF> tag();
}
