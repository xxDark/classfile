package dev.xdark.classfile.opcode;

import dev.xdark.classfile.InvalidClassException;
import dev.xdark.classfile.io.Codec;

import java.util.Arrays;

/**
 * LookupSwitch.
 *
 * @author xDark
 */
public final class LookupSwitchInstruction extends AbstractInstruction<LookupSwitchInstruction> {
    static final Codec<LookupSwitchInstruction> CODEC = Codec.of(input -> {
        input.skipBytes(1);
        int pos = input.position();
        input.skipBytes(pos + (4 - pos & 3));
        int dflt = input.readInt();
        int count = input.readInt();
        int[] keys = new int[count];
        int[] labels = new int[count];
        for (int i = 0; i < count; i++) {
            keys[i] = input.readInt();
            labels[i] = input.readInt();
        }
        return new LookupSwitchInstruction(keys, labels, dflt);
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        int pos = output.position();
        output.position(pos + (4 - pos & 3));
        output.writeInt(value.getDefault());
        int[] keys = value.getKeys();
        int[] labels = value.getLabels();
        int count = keys.length;
        if (count != labels.length) {
            throw new InvalidClassException("Key and label size mismatch");
        }
        output.writeInt(count);
        for (int i = 0; i < count; i++) {
            output.writeInt(keys[i]);
            output.writeInt(labels[i]);
        }
    });

    private final int[] keys;
    private final int[] labels;
    private final int dflt;

    /**
     * @param keys   Lookup keys.
     * @param labels Jump offsets.
     * @param dflt   Default branch.
     * @apiNote Keys and offsets are stored as two separate arrays for
     * user friendliness, ideally we could encode both as a long and store
     * everything in one long array.
     */
    public LookupSwitchInstruction(int[] keys, int[] labels, int dflt) {
        super(Opcode.LOOKUP_SWITCH);
        this.keys = keys;
        this.labels = labels;
        this.dflt = dflt;
    }

    /**
     * @return Default branch.
     */
    public int getDefault() {
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
        int index = Arrays.binarySearch(keys, value);
        return index < 0 ? dflt : labels[index];
    }
}
