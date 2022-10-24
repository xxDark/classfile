package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * ModuleMainClass.
 *
 * @author xDark
 */
public final class ModuleMainClassAttribute implements Attribute<ModuleMainClassAttribute> {
    static final Codec<ModuleMainClassAttribute> CODEC = Codec.of(input -> {
        if (input.readInt() != 2) {
            throw new InvalidAttributeException("Attribute length is not 2");
        }
        return new ModuleMainClassAttribute(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(2);
        output.writeShort(value.getIndex());
    });
    private final int index;

    /**
     * @param index Main class index.
     */
    public ModuleMainClassAttribute(int index) {
        this.index = index;
    }

    /**
     * @return Main class index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public @NotNull AttributeInfo<ModuleMainClassAttribute> info() {
        return AttributeInfo.ModuleMainClass;
    }
}
