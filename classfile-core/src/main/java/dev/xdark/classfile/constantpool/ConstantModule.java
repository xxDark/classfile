package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Package.
 *
 * @author xDark
 */
public final class ConstantModule implements ConstantEntry<ConstantModule> {
    static final Codec<ConstantModule> CODEC = Codec.of(input -> {
        return new ConstantModule(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.index());
    }, Skip.exact(2));

    private final int nameIndex;

    /**
     * @param nameIndex Module name index.
     */
    public ConstantModule(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    /**
     * @return Module name index.
     */
    public int index() {
        return nameIndex;
    }

    @Override
    public @NotNull Tag<ConstantModule> tag() {
        return Tag.CONSTANT_Module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantModule that = (ConstantModule) o;

        return nameIndex == that.nameIndex;
    }

    @Override
    public int hashCode() {
        return nameIndex;
    }
}
