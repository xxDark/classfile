package dev.xdark.classfile.attribute.stackmap.type;

import dev.xdark.classfile.io.Codec;

/**
 * UninitializedThis_variable_info.
 *
 * @author xDark
 */
public final class UninitializedThisVerificationTypeInfo implements VerificationTypeInfo<UninitializedThisVerificationTypeInfo> {
    public static final Codec<UninitializedThisVerificationTypeInfo> CODEC = Codec.singleton(new UninitializedThisVerificationTypeInfo());

    @Override
    public VerificationType<UninitializedThisVerificationTypeInfo> type() {
        return VerificationType.ITEM_UninitializedThis;
    }
}
