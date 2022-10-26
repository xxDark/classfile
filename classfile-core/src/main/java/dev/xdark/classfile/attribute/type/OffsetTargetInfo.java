package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * offset_target.
 *
 * @author xDark
 */
public final class OffsetTargetInfo implements TargetInfo<OffsetTargetInfo> {
    static final Codec<OffsetTargetInfo> CODEC = Codec.of(input -> {
        return new OffsetTargetInfo(TargetType.require(input), input.readUnsignedShort());
    }, (output, value) -> {
        output.writeByte(value.type().kind());
        output.writeShort(value.getOffset());
    }, Skip.exact(3));
    private final TargetType<OffsetTargetInfo> type;
    private final int offset;

    /**
     * @param type   Target type.
     * @param offset Instruction offset.
     */
    public OffsetTargetInfo(TargetType<OffsetTargetInfo> type, int offset) {
        this.type = type;
        this.offset = offset;
    }

    /**
     * @return Instruction offset.
     */
    public int getOffset() {
        return offset;
    }

    @Override
    public @NotNull TargetType<OffsetTargetInfo> type() {
        return type;
    }
}
