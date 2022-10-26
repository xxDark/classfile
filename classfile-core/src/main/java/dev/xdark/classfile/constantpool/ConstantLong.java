package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Integer.
 *
 * @author xDark
 */
public final class ConstantLong implements ConstantEntry<ConstantLong>, ValueEntry<Long> {
    static final Codec<ConstantLong> CODEC = Codec.of(input -> {
        return new ConstantLong(input.readLong());
    }, (output, value) -> {
        output.writeLong(value.value());
    }, Skip.exact(8));

    private final long value;

    /**
     * @param value Integer value.
     */
    public ConstantLong(long value) {
        this.value = value;
    }

    /**
     * @return Long value.
     */
    public long value() {
        return value;
    }

    @Override
    public @NotNull Long getValue() {
        return value;
    }

    @Override
    public @NotNull Tag<ConstantLong> tag() {
        return Tag.CONSTANT_Long;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantLong that = (ConstantLong) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }
}
