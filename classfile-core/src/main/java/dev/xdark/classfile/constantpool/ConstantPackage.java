package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Module.
 *
 * @author xDark
 */
public final class ConstantPackage implements ConstantEntry<ConstantPackage> {
    static final Codec<ConstantPackage> CODEC = Codec.of(input -> {
        return new ConstantPackage(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.index());
    });

    private final int nameIndex;

    /**
     * @param nameIndex Package name index.
     */
    public ConstantPackage(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    /**
     * @return Package name index.
     */
    public int index() {
        return nameIndex;
    }

    @Override
    public @NotNull Tag<ConstantPackage> tag() {
        return Tag.CONSTANT_Package;
    }
}
