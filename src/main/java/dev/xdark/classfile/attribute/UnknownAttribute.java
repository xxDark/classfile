package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;

/**
 * Unknown attribute.
 *
 * @author xDark
 */
public final class UnknownAttribute implements Attribute<UnknownAttribute> {
    public static final Codec<UnknownAttribute> CODEC = Codec.of(input -> {
        return new UnknownAttribute(input.read(input.readInt()));
    }, (output, value) -> {
        byte[] content = value.getData();
        output.writeInt(content.length);
        output.write(content);
    });

    private final byte[] data;

    /**
     * @param content Data.
     */
    public UnknownAttribute(byte[] content) {
        this.data = content;
    }

    /**
     * @return Attribute data.
     */
    public byte[] getData() {
        return data;
    }

    @Override
    public AttributeInfo<UnknownAttribute> info() {
        return AttributeInfo.UNKNOWN;
    }
}
