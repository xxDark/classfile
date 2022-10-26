package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Integer.
 *
 * @author xDark
 */
public final class ConstantInteger implements ConstantEntry<ConstantInteger>, ValueEntry<Integer>  {
    static final Codec<ConstantInteger> CODEC = Codec.of(input -> {
        return new ConstantInteger(input.readInt());
    }, (output, value) -> {
        output.writeInt(value.value());
    }, Skip.exact(4));

    private final int value;

    /**
     * @param value Integer value.
     */
    public ConstantInteger(int value) {
        this.value = value;
    }

    /**
     * @return Integer value.
     */
    public int value() {
        return value;
    }

    @Override
    public @NotNull Integer getValue() {
        return value;
    }

    @Override
    public @NotNull Tag<ConstantInteger> tag() {
        return Tag.CONSTANT_Integer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantInteger that = (ConstantInteger) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
