package dev.xdark.classfile.attribute.stackmap.type;

import dev.xdark.classfile.io.Codec;

/**
 * Null_variable_info.
 *
 * @author xDark
 */
public final class NullVerificationTypeInfo implements VerificationTypeInfo<NullVerificationTypeInfo> {
    public static final Codec<NullVerificationTypeInfo> CODEC = Codec.singleton(new NullVerificationTypeInfo());

    @Override
    public VerificationType<NullVerificationTypeInfo> type() {
        return VerificationType.ITEM_Null;
    }
}
