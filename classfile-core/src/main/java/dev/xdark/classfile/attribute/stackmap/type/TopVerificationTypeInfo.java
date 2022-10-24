package dev.xdark.classfile.attribute.stackmap.type;

import dev.xdark.classfile.io.Codec;

/**
 * Top_variable_info.
 *
 * @author xDark
 */
public final class TopVerificationTypeInfo implements VerificationTypeInfo<TopVerificationTypeInfo> {
    public static final Codec<TopVerificationTypeInfo> CODEC = Codec.singleton(new TopVerificationTypeInfo());

    @Override
    public VerificationType<TopVerificationTypeInfo> type() {
        return VerificationType.ITEM_Top;
    }
}
