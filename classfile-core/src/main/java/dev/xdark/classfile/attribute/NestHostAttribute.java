package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

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
    }, Skip.u32());
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
    public @NotNull AttributeInfo<NestHostAttribute> info() {
        return AttributeInfo.NestHost;
    }
}
