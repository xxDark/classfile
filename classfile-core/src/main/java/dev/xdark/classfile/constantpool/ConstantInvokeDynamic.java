package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_InvokeDynamic.
 *
 * @author xDark
 */
public final class ConstantInvokeDynamic implements ConstantEntry<ConstantInvokeDynamic> {
    static final Codec<ConstantInvokeDynamic> CODEC = Codec.of(input -> {
        return new ConstantInvokeDynamic(input.readUnsignedShort(), input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.bootstrapMethodIndex());
        output.writeShort(value.nameAndTypeIndex());
    });

    private final int bootstrapMethodIndex;
    private final int nameAndTypeIndex;

    /**
     * @param bootstrapMethodIndex Bootstrap method index.
     * @param nameAndTypeIndex     Name and type index.
     */
    public ConstantInvokeDynamic(int bootstrapMethodIndex, int nameAndTypeIndex) {
        this.bootstrapMethodIndex = bootstrapMethodIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    /**
     * @return Bootstrap method index.
     */
    public int bootstrapMethodIndex() {
        return bootstrapMethodIndex;
    }

    /**
     * @return Name and type index.
     */
    public int nameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public @NotNull Tag<ConstantInvokeDynamic> tag() {
        return Tag.CONSTANT_InvokeDynamic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantInvokeDynamic that = (ConstantInvokeDynamic) o;

        if (bootstrapMethodIndex != that.bootstrapMethodIndex) return false;
        return nameAndTypeIndex == that.nameAndTypeIndex;
    }

    @Override
    public int hashCode() {
        int result = bootstrapMethodIndex;
        result = 31 * result + nameAndTypeIndex;
        return result;
    }
}
