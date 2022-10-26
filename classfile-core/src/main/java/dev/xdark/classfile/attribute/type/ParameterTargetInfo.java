package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * type_parameter_target.
 *
 * @author xDark
 */
public final class ParameterTargetInfo implements TargetInfo<ParameterTargetInfo> {
    static final Codec<ParameterTargetInfo> CODEC = Codec.of(input -> {
        return new ParameterTargetInfo(TargetType.require(input), input.readUnsignedByte());
    }, (output, value) -> {
        output.writeByte(value.type().kind());
        output.writeByte(value.getIndex());
    }, Skip.exact(2));
    private final TargetType<ParameterTargetInfo> type;
    private final int index;

    /**
     * @param type Target type.
     * @param index Parameter index.
     */
    public ParameterTargetInfo(TargetType<ParameterTargetInfo> type, int index) {
        this.type = type;
        this.index = index;
    }

    /**
     * @return Parameter index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public @NotNull TargetType<ParameterTargetInfo> type() {
        return type;
    }
}
