package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_InterfaceMethodref.
 *
 * @author xDark
 */
public final class ConstantNameAndType implements ConstantEntry<ConstantNameAndType> {
    static final Codec<ConstantNameAndType> CODEC = Codec.of(input -> {
        return new ConstantNameAndType(input.readUnsignedShort(), input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.nameIndex());
        output.writeShort(value.typeIndex());
    }, Skip.exact(4));

    private final int nameIndex;
    private final int typeIndex;

    /**
     * @param nameIndex Name index.
     * @param typeIndex Type index.
     */
    public ConstantNameAndType(int nameIndex, int typeIndex) {
        this.nameIndex = nameIndex;
        this.typeIndex = typeIndex;
    }

    /**
     * @return Name index.
     */
    public int nameIndex() {
        return nameIndex;
    }

    /**
     * @return Type index.
     */
    public int typeIndex() {
        return typeIndex;
    }

    @Override
    public @NotNull Tag<ConstantNameAndType> tag() {
        return Tag.CONSTANT_NameAndType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantNameAndType that = (ConstantNameAndType) o;

        if (nameIndex != that.nameIndex) return false;
        return typeIndex == that.typeIndex;
    }

    @Override
    public int hashCode() {
        int result = nameIndex;
        result = 31 * result + typeIndex;
        return result;
    }
}
