package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Methodref.
 *
 * @author xDark
 */
public final class ConstantMethodReference implements ConstantEntry<ConstantMethodReference> {
    static final Codec<ConstantMethodReference> CODEC = Codec.of(input -> {
        return new ConstantMethodReference(input.readUnsignedShort(), input.readUnsignedShort());
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
    public ConstantMethodReference(int classIndex, int nameAndTypeIndex) {
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
    public @NotNull Tag<ConstantMethodReference> tag() {
        return Tag.CONSTANT_Methodref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantMethodReference that = (ConstantMethodReference) o;

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
