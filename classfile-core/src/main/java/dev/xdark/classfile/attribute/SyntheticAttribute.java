package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Synthetic.
 *
 * @author xDark
 */
public final class SyntheticAttribute implements Attribute<SyntheticAttribute> {
    private static final SyntheticAttribute INSTANCE = new SyntheticAttribute();
    static final Codec<SyntheticAttribute> CODEC = Codec.of(input -> {
        if (input.readInt() != 0) {
            throw new InvalidAttributeException("Attribute not empty");
        }
        return INSTANCE;
    }, (output, value) -> {
        output.writeInt(0);
    });

    @Override
    public @NotNull AttributeInfo<SyntheticAttribute> info() {
        return AttributeInfo.Synthetic;
    }
}
