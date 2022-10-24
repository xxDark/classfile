package dev.xdark.classfile.attribute.stackmap.frame;

import dev.xdark.classfile.io.Codec;

import java.util.ArrayList;
import java.util.List;

/**
 * StackMap frame types.
 *
 * @author xDark
 */
public final class StackMapFrameType<T extends StackMapFrame<T>> {
    private static final List<StackMapFrameType<?>> ALL_TYPES = new ArrayList<>();
    public static final StackMapFrameType<SameFrame> SAME = make(FrameTypeRange.SAME, SameFrame.CODEC);
    public static final StackMapFrameType<SameLocalsOneStackItemFrame> SAME_LOCALS_1_STACK_ITEM = make(FrameTypeRange.SAME_LOCALS_1_STACK_ITEM, SameLocalsOneStackItemFrame.CODEC);
    public static final StackMapFrameType<SameLocalsOneStackItemExtendedFrame> SAME_LOCALS_1_STACK_ITEM_EXTENDED = make(FrameTypeRange.SAME_LOCALS_1_STACK_ITEM_EXTENDED, SameLocalsOneStackItemExtendedFrame.CODEC);
    public static final StackMapFrameType<ChopFrame> CHOP = make(FrameTypeRange.CHOP, ChopFrame.CODEC);
    public static final StackMapFrameType<SameExtendedFrame> SAME_FRAME_EXTENDED = make(FrameTypeRange.SAME_FRAME_EXTENDED, SameExtendedFrame.CODEC);
    public static final StackMapFrameType<AppendFrame> APPEND = make(FrameTypeRange.APPEND, AppendFrame.CODEC);
    public static final StackMapFrameType<FullFrame> FULL_FRAME = make(FrameTypeRange.FULL_FRAME, FullFrame.CODEC);

    private final FrameTypeRange range;
    private final Codec<T> codec;

    private StackMapFrameType(FrameTypeRange range, Codec<T> codec) {
        this.range = range;
        this.codec = codec;
    }

    /**
     * @param type Type.
     * @return Frame type.
     * @throws IllegalArgumentException If frame type is invalid.
     */
    public static <T extends StackMapFrame<T>> StackMapFrameType<T> of(int type) {
        List<StackMapFrameType<?>> types = ALL_TYPES;
        for (int i = 0, j = types.size(); i < j; i++) {
            StackMapFrameType<?> frameType = types.get(i);
            if (frameType.range().includes(type)) {
                return (StackMapFrameType<T>) frameType;
            }
        }
        throw new IllegalArgumentException(Integer.toString(type));
    }

    /**
     * @return Type range.
     */
    public FrameTypeRange range() {
        return range;
    }

    /**
     * @return Frame codec.
     * @apiNote StackMap frames are special and some
     * data is encoded as types the attribute reader <strong>MUST</strong>
     * set position back after frame type has been read.
     * The codecs will also write frame tag back on it's own as well.
     */
    public Codec<T> codec() {
        return codec;
    }

    private static <T extends StackMapFrame<T>> StackMapFrameType<T> make(FrameTypeRange range, Codec<T> codec) {
        StackMapFrameType<T> type = new StackMapFrameType<>(range, codec);
        ALL_TYPES.add(type);
        return type;
    }
}
