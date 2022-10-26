package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

/**
 * Constant reference.
 *
 * @author xDark
 * @see ConstantMethodReference
 * @see ConstantInterfaceMethodReference
 * @see ConstantFieldReference
 */
public abstract class ConstantReference<SELF extends ConstantReference<SELF>> implements ConstantEntry<SELF> {
    private final int classIndex;
    private final int nameAndTypeIndex;

    /**
     * @param classIndex       Class index.
     * @param nameAndTypeIndex NameAndType index.
     */
    protected ConstantReference(int classIndex, int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    /**
     * @return Class index.
     */
    public final int classIndex() {
        return classIndex;
    }

    /**
     * @return Descriptor index.
     */
    public final int nameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantReference<?> that = (ConstantReference<?>) o;

        if (classIndex != that.classIndex) return false;
        return nameAndTypeIndex == that.nameAndTypeIndex;
    }

    @Override
    public final int hashCode() {
        int result = classIndex;
        result = 31 * result + nameAndTypeIndex;
        return result;
    }

    static <T extends ConstantReference<T>> Codec<T> codec(ConstantReferenceFunction<T> fn) {
        return Codec.of(input -> {
            return fn.create(input.readUnsignedShort(), input.readUnsignedShort());
        }, (output, value) -> {
            output.writeShort(value.classIndex());
            output.writeShort(value.nameAndTypeIndex());
        }, Skip.exact(4));
    }

    interface ConstantReferenceFunction<T extends ConstantReference<T>> {
        T create(int classNameIndex, int nameAndTypeIndex);
    }
}
