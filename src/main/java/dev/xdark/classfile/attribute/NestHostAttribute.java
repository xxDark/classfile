package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;

/**
 * NestHost.
 *
 * @author xDark
 */
public final class NestHostAttribute implements Attribute<NestHostAttribute> {
    static final Codec<NestHostAttribute> CODEC = Codec.of(input -> {
        if (input.readInt() != 2) {
            throw new InvalidAttributeException("Attribute length is not 2");
        }
        return new NestHostAttribute(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(2);
        output.writeShort(value.getIndex());
    });
    private final int index;

    /**
     * @param index Host class index.
     */
    public NestHostAttribute(int index) {
        this.index = index;
    }

    /**
     * @return Host class index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public AttributeInfo<NestHostAttribute> info() {
        return AttributeInfo.NestHost;
    }
}
