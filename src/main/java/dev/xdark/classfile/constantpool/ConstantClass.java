package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;

/**
 * CONSTANT_Utf8.
 *
 * @author xDark
 */
public final class ConstantClass implements ConstantEntry<ConstantClass> {
    static final Codec<ConstantClass> CODEC = Codec.of(input -> {
        return new ConstantClass(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.index());
    });

    private final int value;

    /**
     * @param value UTF-8 index.
     */
    public ConstantClass(int value) {
        this.value = value;
    }

    /**
     * @return UTF-8 index.
     */
    public int index() {
        return value;
    }

    @Override
    public Tag<ConstantClass> tag() {
        return Tag.CONSTANT_Class;
    }
}
