package dev.xdark.classfile.attribute.stackmap.frame;

/**
 * Stack frame tag ranges.
 *
 * @author xDark
 */
public final class FrameTypeRange {
    public static final FrameTypeRange
            SAME = range(0, 63),
            SAME_LOCALS_1_STACK_ITEM = range(64, 127),
            SAME_LOCALS_1_STACK_ITEM_EXTENDED = exact(247),
            CHOP = range(248, 250),
            SAME_FRAME_EXTENDED = exact(251),
            APPEND = range(252, 254),
            FULL_FRAME = exact(255);

    private final int from, to;

    private FrameTypeRange(int from, int to) {
        this.from = from;
        this.to = to;
    }

    /**
     * @return Minimum accepted tag.
     */
    public int getFrom() {
        return from;
    }

    /**
     * @return Maximum accepted tag.
     */
    public int getTo() {
        return to;
    }

    /**
     * @return Exact value.
     * @throws IllegalStateException if range contains more than 1 value.
     */
    public int exact() {
        int from = this.from;
        if (from != to) {
            throw new IllegalStateException();
        }
        return from;
    }

    /**
     * @param tag Tag to check.
     * @return Whether this tag is in range.
     */
    public boolean includes(int tag) {
        return tag >= from && tag <= to;
    }

    private static FrameTypeRange range(int from, int to) {
        return new FrameTypeRange(from, to);
    }

    private static FrameTypeRange exact(int tag) {
        return new FrameTypeRange(tag, tag);
    }
}
