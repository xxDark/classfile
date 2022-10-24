package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Integer.
 *
 * @author xDark
 */
public final class ConstantDouble implements ConstantEntry<ConstantDouble> {
    static final Codec<ConstantDouble> CODEC = Codec.of(input -> {
        return new ConstantDouble(input.readDouble());
    }, (output, value) -> {
        output.writeDouble(value.value());
    });

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

    @Override
    public @NotNull Tag<ConstantDouble> tag() {
        return Tag.CONSTANT_Double;
    }
}
