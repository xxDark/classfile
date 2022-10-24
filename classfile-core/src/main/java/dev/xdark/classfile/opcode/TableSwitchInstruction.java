package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;

/**
 * TableSwitch.
 *
 * @author xDark
 */
public final class TableSwitchInstruction extends AbstractInstruction<TableSwitchInstruction> {
    static final Codec<TableSwitchInstruction> CODEC = Codec.of(input -> {
        input.skipBytes(1);
        int pos = input.position();
        input.skipBytes(pos + (4 - pos & 3));
        int dflt = input.readInt();
        int low = input.readInt();
        int high = input.readInt();
        int[] offsets = new int[high - low + 1];
        for (int i = 0, j = offsets.length; i < j; i++) {
            offsets[i] = input.readInt();
        }
        return new TableSwitchInstruction(low, high, dflt, offsets);
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        int pos = output.position();
        output.position(pos + (4 - pos & 3));
        output.writeInt(value.getDefault());
        output.writeInt(value.getLow());
        output.writeInt(value.getHigh());
        int[] offsets = value.getLabels();
        output.writeInt(offsets.length);
        for (int offset : offsets) {
            output.writeInt(offset);
        }
    });
    private final int low;
    private final int high;
    private final int dflt;
    private final int[] labels;

    /**
     * @param low    Minimum value.
     * @param high   Maximum value.
     * @param dflt   Default branch offset.
     * @param labels Branch offsets.
     */
    public TableSwitchInstruction(int low, int high, int dflt, int[] labels) {
        super(Opcode.TABLE_SWITCH);
        this.low = low;
        this.high = high;
        this.dflt = dflt;
        this.labels = labels;
    }

    /**
     * @return Miniumm value.
     */
    public int getLow() {
        return low;
    }

    /**
     * @return Maximum value.
     */
    public int getHigh() {
        return high;
    }

    /**
     * @return Default branch.
     */
    public int getDefault() {
        return dflt;
    }

    /**
     * @return Branch table.
     */
    public int[] getLabels() {
        return labels;
    }

    /**
     * Utility method to select branch offset.
     *
     * @param value Value to get the offset based off.
     * @return Jump offset.
     */
    public int select(int value) {
        int low = this.low;
        if (value < low || value > high) {
            return dflt;
        }
        return labels[value - low];
    }
}
