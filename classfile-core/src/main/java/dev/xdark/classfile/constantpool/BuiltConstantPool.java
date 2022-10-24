package dev.xdark.classfile.constantpool;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Constant pool.
 *
 * @author xDark
 */
public final class BuiltConstantPool implements ConstantPool {

    private final List<ConstantEntry<?>> entries;
    private final int size;

    /**
     * @param entries List of constant pool entries.
     */
    public BuiltConstantPool(@NotNull List<ConstantEntry<?>> entries) {
        this.entries = entries;
        size = entries.stream().filter(Objects::nonNull).mapToInt(x -> x.tag().size()).sum();
    }

    @Override
    public @NotNull ConstantEntry<?> get(int index) {
        List<ConstantEntry<?>> entries;
        ConstantEntry<?> entry;
        if (index < 1 || index >= (entries = this.entries).size() || (entry = entries.get(index)) == null) {
            throw new IllegalArgumentException(Integer.toString(index));
        }
        return entry;
    }

    @Override
    public <T extends ConstantEntry<T>> @NotNull T get(int index, Tag<T> tag) {
        ConstantEntry<?> entry = get(index);
        if (entry.tag() != tag) {
            throw new IllegalArgumentException("Wrong tag type " + entry.tag());
        }
        return (T) entry;
    }

    @Override
    public int size() {
        return size;
    }

    @NotNull
    @Override
    public Iterator<@NotNull ConstantEntry<?>> iterator() {
        Iterator<ConstantEntry<?>> iterator = entries.iterator();
        return new Iterator<ConstantEntry<?>>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public ConstantEntry<?> next() {
                ConstantEntry<?> entry = iterator.next();
                if (entry == null) {
                    entry = iterator.next();
                }
                return entry;
            }
        };
    }
}
