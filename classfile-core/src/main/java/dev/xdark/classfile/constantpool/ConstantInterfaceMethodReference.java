package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_InterfaceMethodref.
 *
 * @author xDark
 */
public final class ConstantInterfaceMethodReference implements ConstantEntry<ConstantInterfaceMethodReference> {
    static final Codec<ConstantInterfaceMethodReference> CODEC = Codec.of(input -> {
        return new ConstantInterfaceMethodReference(input.readUnsignedShort(), input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.classIndex());
        output.writeShort(value.nameAndTypeIndex());
    });

    private final int classIndex;
    private final int nameAndTypeIndex;

    /**
     * @param classIndex       Class index.
     * @param nameAndTypeIndex NameAndType index.
     */
    public ConstantInterfaceMethodReference(int classIndex, int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    /**
     * @return Class index.
     */
    public int classIndex() {
        return classIndex;
    }

    /**
     * @return Descriptor index.
     */
    public int nameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public @NotNull Tag<ConstantInterfaceMethodReference> tag() {
        return Tag.CONSTANT_InterfaceMethodref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantInterfaceMethodReference that = (ConstantInterfaceMethodReference) o;

        if (classIndex != that.classIndex) return false;
        return nameAndTypeIndex == that.nameAndTypeIndex;
    }

    @Override
    public int hashCode() {
        int result = classIndex;
        result = 31 * result + nameAndTypeIndex;
        return result;
    }
}
