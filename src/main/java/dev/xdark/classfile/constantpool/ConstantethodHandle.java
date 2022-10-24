package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;

/**
 * CONSTANT_InterfaceMethodref.
 *
 * @author xDark
 */
public final class ConstantethodHandle implements ConstantEntry<ConstantethodHandle> {
    static final Codec<ConstantethodHandle> CODEC = Codec.of(input -> {
        return new ConstantethodHandle(input.readUnsignedByte(), input.readUnsignedShort());
    }, (output, value) -> {
        output.writeByte(value.referenceKind());
        output.writeShort(value.referenceIndex());
    });

    private final int referenceKind;
    private final int referenceIndex;

    /**
     * @param referenceKind Reference kind.
     * @param referenceIndex Reference index.
     */
    public ConstantethodHandle(int referenceKind, int referenceIndex) {
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    /**
     * @return Reference kind.
     */
    public int referenceKind() {
        return referenceKind;
    }

    /**
     * @return Reference index.
     */
    public int referenceIndex() {
        return referenceIndex;
    }

    @Override
    public Tag<ConstantethodHandle> tag() {
        return Tag.CONSTANT_MethodHandle;
    }
}
