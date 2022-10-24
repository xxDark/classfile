package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Fieldref.
 *
 * @author xDark
 */
public final class ConstantFieldReference implements ConstantEntry<ConstantFieldReference> {
    static final Codec<ConstantFieldReference> CODEC = Codec.of(input -> {
        return new ConstantFieldReference(input.readUnsignedShort(), input.readUnsignedShort());
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
    public ConstantFieldReference(int classIndex, int nameAndTypeIndex) {
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
    public @NotNull Tag<ConstantFieldReference> tag() {
        return Tag.CONSTANT_Fieldref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantFieldReference that = (ConstantFieldReference) o;

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
