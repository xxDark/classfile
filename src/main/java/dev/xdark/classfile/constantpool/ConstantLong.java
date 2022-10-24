package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;

/**
 * CONSTANT_Integer.
 *
 * @author xDark
 */
public final class ConstantLong implements ConstantEntry<ConstantLong> {
    static final Codec<ConstantLong> CODEC = Codec.of(input -> {
        return new ConstantLong(input.readLong());
    }, (output, value) -> {
        output.writeLong(value.value());
    });

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
    public Tag<ConstantLong> tag() {
        return Tag.CONSTANT_Long;
    }
}
