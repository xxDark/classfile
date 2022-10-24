package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;

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
    });

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
    public Tag<ConstantNameAndType> tag() {
        return Tag.CONSTANT_NameAndType;
    }
}
