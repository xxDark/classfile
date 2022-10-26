package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

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
    }, Skip.u32());

    @Override
    public @NotNull AttributeInfo<DeprecatedAttribute> info() {
        return AttributeInfo.Deprecated;
    }
}
