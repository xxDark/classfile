package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * catch_target.
 *
 * @author xDark
 */
public final class CatchTargetInfo implements TargetInfo<CatchTargetInfo> {
    static final Codec<CatchTargetInfo> CODEC = Codec.of(input -> {
        input.skipBytes(1);
        return new CatchTargetInfo(input.readUnsignedByte());
    }, (output, value) -> {
        output.writeByte(TargetType.CATCH.kind());
        output.writeByte(value.getIndex());
    }, Skip.exact(2));
    private final int index;

    /**
     * @param index Exception table index.
     */
    public CatchTargetInfo(int index) {
        this.index = index;
    }

    /**
     * @return Exception table index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public @NotNull TargetType<CatchTargetInfo> type() {
        return TargetType.CATCH;
    }
}
