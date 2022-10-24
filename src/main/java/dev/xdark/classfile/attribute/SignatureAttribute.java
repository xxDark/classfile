package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;

/**
 * ConstantValue.
 *
 * @author xDark
 */
public final class SignatureAttribute implements Attribute<SignatureAttribute> {
    static final Codec<SignatureAttribute> CODEC = Codec.of(input -> {
        if (input.readInt() != 2) {
            throw new InvalidAttributeException("Attribute length is not 2");
        }
        return new SignatureAttribute(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeInt(2);
        output.writeShort(value.getIndex());
    });
    private final int index;

    /**
     * @param index Signature index.
     */
    public SignatureAttribute(int index) {
        this.index = index;
    }

    /**
     * @return Signature index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public AttributeInfo<SignatureAttribute> info() {
        return AttributeInfo.Signature;
    }
}
