package dev.xdark.classfile.attribute.stackmap.type;

import dev.xdark.classfile.io.Codec;

/**
 * Object_variable_info.
 *
 * @author xDark
 */
public final class ObjectVariableVerificationTypeInfo implements VerificationTypeInfo<ObjectVariableVerificationTypeInfo> {
    public static final Codec<ObjectVariableVerificationTypeInfo> CODEC = Codec.of(input -> {
        return new ObjectVariableVerificationTypeInfo(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.getClassIndex());
    });
    private final int classIndex;

    /**
     * @param classIndex Type index.
     */
    public ObjectVariableVerificationTypeInfo(int classIndex) {
        this.classIndex = classIndex;
    }

    /**
     * @return Class index.
     */
    public int getClassIndex() {
        return classIndex;
    }

    @Override
    public VerificationType<ObjectVariableVerificationTypeInfo> type() {
        return VerificationType.ITEM_Object;
    }
}
