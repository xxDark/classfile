package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;

/**
 * EnclosingMethod.
 *
 * @author xDark
 */
public final class EnclosingMethodAttribute implements Attribute<EnclosingMethodAttribute> {
    static final Codec<EnclosingMethodAttribute> CODEC = Codec.of(input -> {
        if (input.readInt() != 4) {
            throw new InvalidAttributeException("Attribute length is not 4");
        }
        return new EnclosingMethodAttribute(input.readUnsignedShort(), input.readUnsignedShort());
    }, (output, value) -> {
        output.writeInt(4);
        output.writeShort(value.getClassIndex());
        output.writeShort(value.getMethodIndex());
    });
    private final int classIndex;
    private final int methodIndex;

    /**
     * @param classIndex  Class index.
     * @param methodIndex Method index.
     */
    public EnclosingMethodAttribute(int classIndex, int methodIndex) {
        this.classIndex = classIndex;
        this.methodIndex = methodIndex;
    }

    /**
     * @return Class index.
     */
    public int getClassIndex() {
        return classIndex;
    }

    /**
     * @return Method index.
     */
    public int getMethodIndex() {
        return methodIndex;
    }

    @Override
    public AttributeInfo<EnclosingMethodAttribute> info() {
        return AttributeInfo.EnclosingMethod;
    }
}
