package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;

/**
 * ModulePackages.
 *
 * @author xDark
 */
public final class ModulePackagesAttribute implements Attribute<ModulePackagesAttribute> {
    static final Codec<ModulePackagesAttribute> CODEC = Codec.of(input -> {
        int length = input.readUnsignedShort();
        if (length < 2) {
            throw new InvalidAttributeException("Length is less than 2");
        }
        int count = input.readUnsignedShort();
        if (length != (count + 1) * 2) {
            throw new InvalidAttributeException("Invalid attribute content");
        }
        return new ModulePackagesAttribute(AttributeUtil.readUnsignedShorts(input, count));
    }, (output, value) -> {
        int[] packages = value.getPackages();
        output.writeShort((packages.length + 1) * 2);
        AttributeUtil.writeUnsignedShorts(output, packages);
    });
    private final int[] packages;

    /**
     * @param packages Package indices.
     */
    public ModulePackagesAttribute(int[] packages) {
        this.packages = packages;
    }

    /**
     * @return Package indices.
     */
    public int[] getPackages() {
        return packages;
    }

    @Override
    public AttributeInfo<ModulePackagesAttribute> info() {
        return AttributeInfo.ModulePackages;
    }
}
