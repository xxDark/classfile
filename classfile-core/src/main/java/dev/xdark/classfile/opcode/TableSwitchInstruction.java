package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * TableSwitch.
 *
 * @author xDark
 */
public final class TableSwitchInstruction
        extends AbstractInstruction<TableSwitchInstruction>
        implements FlowInstruction {
    static final Codec<TableSwitchInstruction> CODEC = Codec.of(input -> {
        int bytecodePosition = input.position();
        input.skipBytes(1);
        int pos = input.position();
        input.skipBytes(4 - pos & 3);
        Label dflt = new Label(bytecodePosition + input.readInt());
        int low = input.readInt();
        int high = input.readInt();
        Label[] offsets = new Label[high - low + 1];
        for (int i = 0, j = offsets.length; i < j; i++) {
            offsets[i] = new Label(bytecodePosition + input.readInt());
        }
        return new TableSwitchInstruction(low, high, dflt, offsets);
    }, (output, value) -> {
        int bytecodePosition = output.position();
        output.writeByte(Opcode.TABLE_SWITCH.opcode());
        int pos = output.position();
        output.position(pos + (4 - pos & 3));
        value.getDefault().write(bytecodePosition, output, true);
        output.writeInt(value.getLow());
        output.writeInt(value.getHigh());
        Label[] offsets = value.getLabels();
        output.writeInt(offsets.length);
        for (Label offset : offsets) {
            offset.write(bytecodePosition, output, true);
        }
    }, Skip.exact(1).then(input -> {
        int pos = input.position();
        input.position(pos + (4 - pos & 3));
        input.skipBytes(12);
        input.skipBytes(input.readInt() * 4);
    }));
    private final int low;
    private final int high;
    private final Label dflt;
    private final Label[] labels;

    /**
     * @param low    Minimum value.
     * @param high   Maximum value.
     * @param dflt   Default branch offset.
     * @param labels Branch offsets.
     */
    public TableSwitchInstruction(int low, int high, Label dflt, Label[] labels) {
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
    public Label getDefault() {
        return dflt;
    }

    /**
     * @return Branch table.
     */
    public Label[] getLabels() {
        return labels;
    }

    /**
     * Utility method to select branch offset.
     *
     * @param value Value to get the offset based off.
     * @return Jump offset.
     */
    public Label select(int value) {
        int low = this.low;
        if (value < low || value > high) {
            return dflt;
        }
        return labels[value - low];
    }

    @Override
    public @NotNull List<Label> getTargets() {
        Label[] labels = this.labels;
        Label[] targets = new Label[labels.length + 1];
        System.arraycopy(labels, 0, targets, 0, labels.length);
        targets[labels.length] = dflt;
        return Arrays.asList(targets);
    }
}
