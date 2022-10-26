package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * supertype_target.
 *
 * @author xDark
 */
public final class SuperTypeTargetInfo implements TargetInfo<SuperTypeTargetInfo> {
    static final Codec<SuperTypeTargetInfo> CODEC = Codec.of(input -> {
        input.skipBytes(1);
        return new SuperTypeTargetInfo(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeByte(TargetType.SUPER_TYPE.kind());
        output.writeShort(value.getIndex());
    }, Skip.exact(3));
    private final int index;

    /**
     * @param index Super class index.
     */
    public SuperTypeTargetInfo(int index) {
        this.index = index;
    }

    /**
     * @return Super class index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public @NotNull TargetType<SuperTypeTargetInfo> type() {
        return TargetType.SUPER_TYPE;
    }
}
