package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * formal_parameter_target.
 *
 * @author xDark
 */
public final class FormalParameterTargetInfo implements TargetInfo<FormalParameterTargetInfo> {
    static final Codec<FormalParameterTargetInfo> CODEC = Codec.of(input -> {
        return new FormalParameterTargetInfo(input.readUnsignedByte());
    }, (output, value) -> {
        output.writeByte(value.getIndex());
    });
    private final int index;

    /**
     * @param index Formal parameter index.
     */
    public FormalParameterTargetInfo(int index) {
        this.index = index;
    }

    /**
     * @return Formal parameter index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public @NotNull TargetType<FormalParameterTargetInfo> type() {
        return TargetType.FORMAL_PARAMETER;
    }
}
