package dev.xdark.classfile.opcode;

import dev.xdark.classfile.InvalidClassException;
import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * LookupSwitch.
 *
 * @author xDark
 */
public final class LookupSwitchInstruction
        extends AbstractInstruction<LookupSwitchInstruction>
        implements FlowInstruction {
    static final Codec<LookupSwitchInstruction> CODEC = Codec.of(input -> {
        int bytecodePosition = input.position();
        input.skipBytes(1);
        int pos = input.position();
        input.skipBytes(4 - pos & 3);
        Label dflt = new Label(bytecodePosition + input.readInt());
        int count = input.readInt();
        int[] keys = new int[count];
        Label[] labels = new Label[count];
        for (int i = 0; i < count; i++) {
            keys[i] = input.readInt();
            labels[i] = new Label(bytecodePosition + input.readInt());
        }
        return new LookupSwitchInstruction(keys, labels, dflt);
    }, (output, value) -> {
        int bytecodePosition = output.position();
        output.writeByte(value.getOpcode().opcode());
        int pos = output.position();
        output.position(pos + (4 - pos & 3));
        value.getDefault().write(bytecodePosition, output, true);
        int[] keys = value.getKeys();
        Label[] labels = value.getLabels();
        int count = keys.length;
        if (count != labels.length) {
            throw new InvalidClassException("Key and label size mismatch");
        }
        output.writeInt(count);
        for (int i = 0; i < count; i++) {
            output.writeInt(keys[i]);
            labels[i].write(bytecodePosition, output, true);
        }
    });

    private final int[] keys;
    private final Label[] labels;
    private final Label dflt;

    /**
     * @param keys   Lookup keys.
     * @param labels Jump offsets.
     * @param dflt   Default branch.
     * @apiNote Keys and offsets are stored as two separate arrays for
     * user friendliness, ideally we could encode both as a long and store
     * everything in one long array.
     */
    public LookupSwitchInstruction(int[] keys, Label[] labels, Label dflt) {
        super(Opcode.LOOKUP_SWITCH);
        this.keys = keys;
        this.labels = labels;
        this.dflt = dflt;
    }

    /**
     * @return Default branch.
     */
    public Label getDefault() {
        return dflt;
    }

    /**
     * @return Lookup keys.
     */
    public int[] getKeys() {
        return keys;
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
        int index = Arrays.binarySearch(keys, value);
        return index < 0 ? dflt : labels[index];
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
