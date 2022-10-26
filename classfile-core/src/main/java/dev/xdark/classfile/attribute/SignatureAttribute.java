package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

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
    }, Skip.u32());
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
    public @NotNull AttributeInfo<SignatureAttribute> info() {
        return AttributeInfo.Signature;
    }
}
