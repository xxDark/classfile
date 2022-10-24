package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Integer.
 *
 * @author xDark
 */
public final class ConstantFloat implements ConstantEntry<ConstantFloat> {
    static final Codec<ConstantFloat> CODEC = Codec.of(input -> {
        return new ConstantFloat(input.readFloat());
    }, (output, value) -> {
        output.writeFloat(value.value());
    });

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
    public @NotNull Tag<ConstantFloat> tag() {
        return Tag.CONSTANT_Float;
    }
}
