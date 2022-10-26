package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

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
    }, Skip.exact(3));

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
    public @NotNull Tag<ConstantethodHandle> tag() {
        return Tag.CONSTANT_MethodHandle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantethodHandle that = (ConstantethodHandle) o;

        if (referenceKind != that.referenceKind) return false;
        return referenceIndex == that.referenceIndex;
    }

    @Override
    public int hashCode() {
        int result = referenceKind;
        result = 31 * result + referenceIndex;
        return result;
    }
}
