package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Utf8.
 *
 * @author xDark
 */
public final class ConstantClass implements ConstantEntry<ConstantClass> {
    static final Codec<ConstantClass> CODEC = Codec.of(input -> {
        return new ConstantClass(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.index());
    }, Skip.exact(2));

    private final int value;

    /**
     * @param value UTF-8 index.
     */
    public ConstantClass(int value) {
        this.value = value;
    }

    /**
     * @return UTF-8 index.
     */
    public int index() {
        return value;
    }

    @Override
    public @NotNull Tag<ConstantClass> tag() {
        return Tag.CONSTANT_Class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantClass that = (ConstantClass) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
