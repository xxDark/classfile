package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Input;

import java.io.IOException;

/**
 * Target type for target_info union.
 *
 * @author xDark
 */
public final class TargetType<T extends TargetInfo<T>> {
    private static final TargetType<?>[] ALL_TYPES = new TargetType[76];
    public static final TargetType<ParameterTargetInfo>
            PARAMETER_OF_GENERIC_CLASS_OR_INTERFACE = make(0x00, ParameterTargetInfo.CODEC),
            PARAMETER_OF_GENERIC_METHOD_OR_CONSTRUCTOR = make(0x01, ParameterTargetInfo.CODEC);
    public static final TargetType<SuperTypeTargetInfo> SUPER_TYPE = make(0x10, SuperTypeTargetInfo.CODEC);
    public static final TargetType<ParameterBoundTargetInfo>
            PARAMETER_BOUND_OF_GENERIC_CLASS_OR_INTERFACE = make(0x11, ParameterBoundTargetInfo.CODEC),
            PARAMETER_BOUND_OF_GENERIC_METHOD_OR_CONSTRUCTOR = make(0x12, ParameterBoundTargetInfo.CODEC);
    public static final TargetType<EmptyTargetInfo>
            FIELD_OR_RECORD = make(0x13, EmptyTargetInfo.CODEC),
            RETURN_TYPE_OR_NEW_OBJECT = make(0x14, EmptyTargetInfo.CODEC),
            RECEIVER_TYPE = make(0x15, EmptyTargetInfo.CODEC);
    public static final TargetType<FormalParameterTargetInfo> FORMAL_PARAMETER = make(0x16, FormalParameterTargetInfo.CODEC);
    public static final TargetType<ThrowsTargetInfo> THROWS = make(0x17, ThrowsTargetInfo.CODEC);
    public static final TargetType<LocalVariableTargetInfo>
            LOCAL_VARIABLE_DECLARATION = make(0x40, LocalVariableTargetInfo.CODEC),
            RESOURCE_VARIABLE_DECLARATION = make(0x41, LocalVariableTargetInfo.CODEC);
    public static final TargetType<CatchTargetInfo> CATCH = make(0x42, CatchTargetInfo.CODEC);
    public static final TargetType<OffsetTargetInfo>
            INSTANCEOF = make(0x43, OffsetTargetInfo.CODEC),
            NEW = make(0x44, OffsetTargetInfo.CODEC),
            REF_NEW = make(0x45, OffsetTargetInfo.CODEC),
            REF_METHOD = make(0x46, OffsetTargetInfo.CODEC);
    public static final TargetType<TypeArgumentInfo>
            CAST = make(0x47, TypeArgumentInfo.CODEC),
            TYPE_GENERIC_NEW_INVOCATION = make(0x48, TypeArgumentInfo.CODEC),
            TYPE_GENERIC_METHOD_INVOCATION = make(0x49, TypeArgumentInfo.CODEC),
            TYPE_GENERIC_NEW_REF_INVOCATION = make(0x4A, TypeArgumentInfo.CODEC),
            TYPE_GENERIC_METHOD_REF_INVOCATION = make(0x4B, TypeArgumentInfo.CODEC);


    private final int kind;
    private final Codec<T> codec;

    /**
     * @param kind  Target kind.
     * @param codec Target codec.
     */
    private TargetType(int kind, Codec<T> codec) {
        this.kind = kind;
        this.codec = codec;
    }

    public static <T extends TargetInfo<T>> TargetType<T> of(int kind) {
        TargetType<?>[] types;
        if (kind < 0 || kind >= (types = ALL_TYPES).length) {
            return null;
        }
        return (TargetType<T>) types[kind];
    }

    /**
     * @return Target kind.
     */
    public int kind() {
        return kind;
    }

    /**
     * @return Target codec.
     */
    public Codec<T> codec() {
        return codec;
    }

    private static <T extends TargetInfo<T>> TargetType<T> make(int kind, Codec<T> codec) {
        return new TargetType<>(kind, codec);
    }

    static <T extends TargetInfo<T>> TargetType<T> require(Input input) throws IOException {
        int kind = input.readUnsignedByte();
        TargetType<T> type = TargetType.of(kind);
        if (type == null) {
            throw new InvalidAttributeException("unknown target " + kind);
        }
        return type;
    }
}
