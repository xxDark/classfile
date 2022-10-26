package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * ConstantValue.
 *
 * @author xDark
 */
public final class ConstantValueAttribute implements Attribute<ConstantValueAttribute> {
    static final Codec<ConstantValueAttribute> CODEC = Codec.of(input -> {
        if (input.readInt() != 2) {
            throw new InvalidAttributeException("Attribute length is not 2");
        }
        return new ConstantValueAttribute(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(2);
        output.writeShort(value.getIndex());
    }, Skip.u32());
    private final int index;

    /**
     * @param index Constant index.
     */
    public ConstantValueAttribute(int index) {
        this.index = index;
    }

    /**
     * @return Constant index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public @NotNull AttributeInfo<ConstantValueAttribute> info() {
        return AttributeInfo.ConstantValue;
    }
}
