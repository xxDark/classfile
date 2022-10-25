package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * type_parameter_target.
 *
 * @author xDark
 */
public final class ParameterBoundTargetInfo implements TargetInfo<ParameterBoundTargetInfo> {
    static final Codec<ParameterBoundTargetInfo> CODEC = Codec.of(input -> {
        return new ParameterBoundTargetInfo(TargetType.require(input), input.readUnsignedByte(), input.readUnsignedByte());
    }, (output, value) -> {
        output.writeByte(value.getParameterIndex());
        output.writeByte(value.getBoundIndex());
    });
    private final TargetType<ParameterBoundTargetInfo> type;
    private final int parameterIndex;
    private final int boundIndex;

    /**
     * @param type       Target type.
     * @param index      Parameter index.
     * @param boundIndex Bound index.
     */
    public ParameterBoundTargetInfo(TargetType<ParameterBoundTargetInfo> type, int index, int boundIndex) {
        this.type = type;
        this.parameterIndex = index;
        this.boundIndex = boundIndex;
    }

    /**
     * @return Parameter index.
     */
    public int getParameterIndex() {
        return parameterIndex;
    }

    /**
     * @return Bound index.
     */
    public int getBoundIndex() {
        return boundIndex;
    }

    @Override
    public @NotNull TargetType<ParameterBoundTargetInfo> type() {
        return type;
    }
}
