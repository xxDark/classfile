package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;

/**
 * iinc.
 *
 * @author xDark
 */
public final class IincInstruction extends AbstractInstruction<IincInstruction> {
    static final Codec<IincInstruction> CODEC = Codec.of(input -> {
        input.skipBytes(1);
        return new IincInstruction(input.readUnsignedByte(), input.readByte());
    }, (output, value) -> {
        output.writeByte(Opcode.IINC.opcode());
        output.writeByte(value.getIndex());
        output.writeByte(value.getValue());
    });
    private final int index;
    private final int value;

    /**
     * @param index Variable index.
     * @param value Incremented by.
     */
    public IincInstruction(int index, int value) {
        super(Opcode.IINC);
        this.index = index;
        this.value = value;
    }

    /**
     * @return Variable index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return Incremented by.
     */
    public int getValue() {
        return value;
    }
}
