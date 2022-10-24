package dev.xdark.classfile.attribute.stackmap.type;

import dev.xdark.classfile.io.Codec;

/**
 * Integer_variable_info.
 *
 * @author xDark
 */
public final class IntegerVerificationTypeInfo implements VerificationTypeInfo<IntegerVerificationTypeInfo> {
    public static final Codec<IntegerVerificationTypeInfo> CODEC = Codec.singleton(new IntegerVerificationTypeInfo());

    @Override
    public VerificationType<IntegerVerificationTypeInfo> type() {
        return VerificationType.ITEM_Integer;
    }
}
