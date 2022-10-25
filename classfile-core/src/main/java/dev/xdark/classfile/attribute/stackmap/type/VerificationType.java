package dev.xdark.classfile.attribute.stackmap.type;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.Nullable;

/**
 * Verification types.
 *
 * @author xDark
 */
public final class VerificationType<T extends VerificationTypeInfo<T>> {
    private static final VerificationType<?>[] ALL_TYPES = new VerificationType[9];
    public static final VerificationType<TopVerificationTypeInfo> ITEM_Top = make(0, TopVerificationTypeInfo.CODEC);
    public static final VerificationType<IntegerVerificationTypeInfo> ITEM_Integer = make(1, IntegerVerificationTypeInfo.CODEC);
    public static final VerificationType<FloatVerificationTypeInfo> ITEM_Float = make(2, FloatVerificationTypeInfo.CODEC);
    public static final VerificationType<DoubleVerificationTypeInfo> ITEM_Double = make(3, DoubleVerificationTypeInfo.CODEC);
    public static final VerificationType<LongVerificationTypeInfo> ITEM_Long = make(4, LongVerificationTypeInfo.CODEC);
    public static final VerificationType<NullVerificationTypeInfo> ITEM_Null = make(5, NullVerificationTypeInfo.CODEC);
    public static final VerificationType<UninitializedThisVerificationTypeInfo> ITEM_UninitializedThis = make(6, UninitializedThisVerificationTypeInfo.CODEC);
    public static final VerificationType<ObjectVariableVerificationTypeInfo> ITEM_Object = make(7, ObjectVariableVerificationTypeInfo.CODEC);
    public static final VerificationType<UninitializedVariableVerificationTypeInfo> ITEM_Uninitialized = make(8, UninitializedVariableVerificationTypeInfo.CODEC);

    private final int tag;
    private final Codec<T> codec;

    private VerificationType(int tag, Codec<T> codec) {
        this.tag = tag;
        this.codec = codec;
    }

    /**
     * @param id Verification type ID.
     * @return Verification type by it's id or {@code null},
     * if it is invalid
     */
    // TODO fix me
    @Nullable
    public static <T extends VerificationTypeInfo<T>> VerificationType<T> of(int id) {
        VerificationType<?>[] tags;
        if (id < 0 || id >= (tags = ALL_TYPES).length) {
            return null;
        }
        return (VerificationType<T>) tags[id];
    }

    /**
     * @return Verification tag.
     */
    public int tag() {
        return tag;
    }

    /**
     * @return Info codec.
     */
    public Codec<T> codec() {
        return codec;
    }

    private static <T extends VerificationTypeInfo<T>> VerificationType<T> make(int tag, Codec<T> codec) {
        VerificationType<T> type = new VerificationType<>(tag, codec);
        ALL_TYPES[tag] = type;
        return type;
    }
}
