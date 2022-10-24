package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_MethodType.
 *
 * @author xDark
 */
public final class ConstantMethodType implements ConstantEntry<ConstantMethodType> {
    static final Codec<ConstantMethodType> CODEC = Codec.of(input -> {
        return new ConstantMethodType(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.descriptorIndex());
    });
    private final int descriptorIndex;

    /**
     * @param descriptorIndex Descriptor index.
     */
    public ConstantMethodType(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    /**
     * @return Descriptor index.
     */
    public int descriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public @NotNull Tag<ConstantMethodType> tag() {
        return Tag.CONSTANT_MethodType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantMethodType that = (ConstantMethodType) o;

        return descriptorIndex == that.descriptorIndex;
    }

    @Override
    public int hashCode() {
        return descriptorIndex;
    }
}
