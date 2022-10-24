package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * PermittedSubclasses.
 */
public final class PermittedSubclassesAttribute implements Attribute<PermittedSubclassesAttribute> {
    static final Codec<PermittedSubclassesAttribute> CODEC = Codec.of(input -> {
        int length = input.readInt();
        if (length < 2) {
            throw new InvalidAttributeException("Length is less than 2");
        }
        int count = input.readUnsignedShort();
        if (length != (count + 1) * 2) {
            throw new InvalidAttributeException("Invalid attribute content");
        }
        return new PermittedSubclassesAttribute(AttributeUtil.readUnsignedShorts(input, count));
    }, (output, value) -> {
        int[] classes = value.getClasses();
        output.writeInt((classes.length + 1) * 2);
        AttributeUtil.writeUnsignedShorts(output, classes);
    });
    private final int[] classes;

    /**
     * @param classes Class indices.
     */
    public PermittedSubclassesAttribute(int[] classes) {
        this.classes = classes;
    }

    /**
     * @return Class indices.
     */
    public int[] getClasses() {
        return classes;
    }

    @Override
    public @NotNull AttributeInfo<PermittedSubclassesAttribute> info() {
        return AttributeInfo.PermittedSubclasses;
    }
}
