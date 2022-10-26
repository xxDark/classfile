package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Integer.
 *
 * @author xDark
 */
public final class ConstantFloat implements ConstantEntry<ConstantFloat>, ValueEntry<Float> {
    static final Codec<ConstantFloat> CODEC = Codec.of(input -> {
        return new ConstantFloat(input.readFloat());
    }, (output, value) -> {
        output.writeFloat(value.value());
    }, Skip.exact(4));

    private final float value;

    /**
     * @param value Float value.
     */
    public ConstantFloat(float value) {
        this.value = value;
    }

    /**
     * @return Float value.
     */
    public float value() {
        return value;
    }

    @Override
    public @NotNull Float getValue() {
        return value;
    }

    @Override
    public @NotNull Tag<ConstantFloat> tag() {
        return Tag.CONSTANT_Float;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantFloat that = (ConstantFloat) o;

        return Float.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }
}
