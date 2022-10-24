package dev.xdark.classfile.constantpool;

import org.jetbrains.annotations.NotNull;

/**
 * Constant pool.
 *
 * @author xDark
 */
public interface ConstantPool extends Iterable<ConstantEntry<?>> {

    /**
     * @param index Entry index.
     * @return Entry.
     * @throws IllegalArgumentException If index is out of bounds,
     *                                  or if the index points to the reserved slot.
     */
    @NotNull ConstantEntry<?> get(int index);

    /**
     * @param index Entry index.
     * @param tag   Entry tag.
     * @return Entry.
     * @throws IllegalArgumentException If tag does not match.
     */
    @NotNull <T extends ConstantEntry<T>> T get(int index, Tag<T> tag);

    /**
     * @return Constant pool size.
     */
    int size();
}
