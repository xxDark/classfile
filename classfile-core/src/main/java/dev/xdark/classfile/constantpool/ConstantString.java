package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Utf8.
 *
 * @author xDark
 */
public final class ConstantString implements ConstantEntry<ConstantString> {
    static final Codec<ConstantString> CODEC = Codec.of(input -> {
        return new ConstantString(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.index());
    }, Skip.exact(2));

    private final int index;

    /**
     * @param index UTF-8 index.
     */
    public ConstantString(int index) {
        this.index = index;
    }

    /**
     * @return UTF-8 index.
     */
    public int index() {
        return index;
    }

    @Override
    public @NotNull Tag<ConstantString> tag() {
        return Tag.CONSTANT_String;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantString that = (ConstantString) o;

        return index == that.index;
    }

    @Override
    public int hashCode() {
        return index;
    }
}
