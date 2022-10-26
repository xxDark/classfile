package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * throws_target.
 *
 * @author xDark
 */
public final class ThrowsTargetInfo implements TargetInfo<ThrowsTargetInfo> {
    static final Codec<ThrowsTargetInfo> CODEC = Codec.of(input -> {
        input.skipBytes(1);
        return new ThrowsTargetInfo(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeByte(TargetType.THROWS.kind());
        output.writeByte(value.getIndex());
    }, Skip.exact(2));
    private final int index;

    /**
     * @param index Type index.
     */
    public ThrowsTargetInfo(int index) {
        this.index = index;
    }

    /**
     * @return Type index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public @NotNull TargetType<ThrowsTargetInfo> type() {
        return TargetType.THROWS;
    }
}
