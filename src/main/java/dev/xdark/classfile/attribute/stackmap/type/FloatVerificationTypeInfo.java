package dev.xdark.classfile.attribute.stackmap.type;

import dev.xdark.classfile.io.Codec;

/**
 * Float_variable_info.
 *
 * @author xDark
 */
public final class FloatVerificationTypeInfo implements VerificationTypeInfo<FloatVerificationTypeInfo> {
    public static final Codec<FloatVerificationTypeInfo> CODEC = Codec.singleton(new FloatVerificationTypeInfo());

    @Override
    public VerificationType<FloatVerificationTypeInfo> type() {
        return VerificationType.ITEM_Float;
    }
}
