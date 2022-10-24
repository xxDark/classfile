package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;

/**
 * CONSTANT_Utf8.
 *
 * @author xDark
 */
public final class ConstantString implements ConstantEntry<ConstantString> {
    static final Codec<ConstantString> CODEC = Codec.of(input -> {
        return new ConstantString(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.index());
    });

    private final int index;

    /**
     * @param index UTF-8 index.
     */
    public ConstantString(int index) {
        this.index = index;
    }

    /**
     * @return UTF-8 index.
     */
    public int index() {
        return index;
    }

    @Override
    public Tag<ConstantString> tag() {
        return Tag.CONSTANT_String;
    }
}
