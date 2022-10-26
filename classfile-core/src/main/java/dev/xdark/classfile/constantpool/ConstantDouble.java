package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Integer.
 *
 * @author xDark
 */
public final class ConstantDouble implements ConstantEntry<ConstantDouble>, ValueEntry<Double> {
    static final Codec<ConstantDouble> CODEC = Codec.of(input -> {
        return new ConstantDouble(input.readDouble());
    }, (output, value) -> {
        output.writeDouble(value.value());
    }, Skip.exact(8));

    private final double value;

    /**
     * @param value Integer value.
     */
    public ConstantDouble(double value) {
        this.value = value;
    }

    /**
     * @return Double value.
     */
    public double value() {
        return value;
    }

    @NotNull
    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public @NotNull Tag<ConstantDouble> tag() {
        return Tag.CONSTANT_Double;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantDouble that = (ConstantDouble) o;

        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
