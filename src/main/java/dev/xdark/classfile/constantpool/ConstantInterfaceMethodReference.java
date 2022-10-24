package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;

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
    public Tag<ConstantInterfaceMethodReference> tag() {
        return Tag.CONSTANT_InterfaceMethodref;
    }
}
