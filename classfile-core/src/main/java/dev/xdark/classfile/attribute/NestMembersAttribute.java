package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * NestMembers.
 */
public final class NestMembersAttribute implements Attribute<NestMembersAttribute> {
    static final Codec<NestMembersAttribute> CODEC = Codec.of(input -> {
        int length = input.readInt();
        if (length < 2) {
            throw new InvalidAttributeException("Length is less than 2");
        }
        int count = input.readUnsignedShort();
        if (length != (count + 1) * 2) {
            throw new InvalidAttributeException("Invalid attribute content");
        }
        return new NestMembersAttribute(AttributeUtil.readUnsignedShorts(input, count));
    }, (output, value) -> {
        int[] classes = value.getClasses();
        output.writeInt((classes.length + 1) * 2);
        AttributeUtil.writeUnsignedShorts(output, classes);
    });
    private final int[] classes;

    /**
     * @param classes Class indices.
     */
    public NestMembersAttribute(int[] classes) {
        this.classes = classes;
    }

    /**
     * @return Class indices.
     */
    public int[] getClasses() {
        return classes;
    }

    @Override
    public @NotNull AttributeInfo<NestMembersAttribute> info() {
        return AttributeInfo.NestMembers;
    }
}
