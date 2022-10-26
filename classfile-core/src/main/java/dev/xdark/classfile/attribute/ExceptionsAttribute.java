package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * Exceptions.
 */
public final class ExceptionsAttribute implements Attribute<ExceptionsAttribute> {
    static final Codec<ExceptionsAttribute> CODEC = Codec.of(input -> {
        int length = input.readInt();
        if (length < 2) {
            throw new InvalidAttributeException("Length is less than 2");
        }
        int count = input.readUnsignedShort();
        if (length != (count + 1) * 2) {
            throw new InvalidAttributeException("Invalid attribute content");
        }
        return new ExceptionsAttribute(AttributeUtil.readUnsignedShorts(input, count));
    }, (output, value) -> {
        int[] packages = value.getExceptions();
        output.writeInt((packages.length + 1) * 2);
        AttributeUtil.writeUnsignedShorts(output, packages);
    }, Skip.u32());
    private final int[] exceptions;

    /**
     * @param exceptions Exception indices.
     */
    public ExceptionsAttribute(int[] exceptions) {
        this.exceptions = exceptions;
    }

    /**
     * @return Exception indices.
     */
    public int[] getExceptions() {
        return exceptions;
    }

    @Override
    public @NotNull AttributeInfo<ExceptionsAttribute> info() {
        return AttributeInfo.Exceptions;
    }
}
