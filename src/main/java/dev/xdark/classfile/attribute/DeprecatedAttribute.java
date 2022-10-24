package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;

/**
 * Deprecated.
 *
 * @author xDark
 */
public final class DeprecatedAttribute implements Attribute<DeprecatedAttribute> {
    private static final DeprecatedAttribute INSTANCE = new DeprecatedAttribute();
    static final Codec<DeprecatedAttribute> CODEC = Codec.of(input -> {
        if (input.readInt() != 0) {
            throw new InvalidAttributeException("Attribute not empty");
        }
        return INSTANCE;
    }, (output, value) -> {
        output.writeInt(0);
    });

    @Override
    public AttributeInfo<DeprecatedAttribute> info() {
        return AttributeInfo.Deprecated;
    }
}
