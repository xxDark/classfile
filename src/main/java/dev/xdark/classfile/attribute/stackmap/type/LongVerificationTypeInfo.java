package dev.xdark.classfile.attribute.stackmap.type;

import dev.xdark.classfile.io.Codec;

/**
 * Long_variable_info.
 *
 * @author xDark
 */
public final class LongVerificationTypeInfo implements VerificationTypeInfo<LongVerificationTypeInfo> {
    public static final Codec<LongVerificationTypeInfo> CODEC = Codec.singleton(new LongVerificationTypeInfo());

    @Override
    public VerificationType<LongVerificationTypeInfo> type() {
        return VerificationType.ITEM_Long;
    }
}
