package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;

/**
 * CONSTANT_Integer.
 *
 * @author xDark
 */
public final class ConstantInteger implements ConstantEntry<ConstantInteger> {
    static final Codec<ConstantInteger> CODEC = Codec.of(input -> {
        return new ConstantInteger(input.readInt());
    }, (output, value) -> {
        output.writeInt(value.value());
    });

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
    public Tag<ConstantInteger> tag() {
        return Tag.CONSTANT_Integer;
    }
}
