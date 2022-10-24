package dev.xdark.classfile.attribute.stackmap.type;

import dev.xdark.classfile.io.Codec;

/**
 * Double_variable_info.
 *
 * @author xDark
 */
public final class DoubleVerificationTypeInfo implements VerificationTypeInfo<DoubleVerificationTypeInfo> {
    public static final Codec<DoubleVerificationTypeInfo> CODEC = Codec.singleton(new DoubleVerificationTypeInfo());

    @Override
    public VerificationType<DoubleVerificationTypeInfo> type() {
        return VerificationType.ITEM_Double;
    }
}
