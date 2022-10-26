package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * type_argument_target.
 *
 * @author xDark
 */
public final class TypeArgumentInfo implements TargetInfo<TypeArgumentInfo> {
    static final Codec<TypeArgumentInfo> CODEC = Codec.of(input -> {
        return new TypeArgumentInfo(TargetType.require(input), input.readUnsignedShort(), input.readUnsignedByte());
    }, (output, value) -> {
        output.writeShort(value.getOffset());
        output.writeByte(value.getIndex());
    }, Skip.exact(3));
    private final TargetType<TypeArgumentInfo> type;
    private final int offset;
    private final int index;

    /**
     * @param type Target type.
     * @param offset     Instruction offset.
     * @param index      Argument index.
     */
    public TypeArgumentInfo(TargetType<TypeArgumentInfo> type, int offset, int index) {
        this.type = type;
        this.offset = offset;
        this.index = index;
    }

    /**
     * @return Instruction offset.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return Argument index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public @NotNull TargetType<TypeArgumentInfo> type() {
        return type;
    }
}
