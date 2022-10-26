package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

/**
 * Field operation instruction.
 *
 * @author xDark
 */
public final class FieldInstruction extends AbstractInstruction<FieldInstruction> {
    static final Codec<FieldInstruction> CODEC = Codec.of(input -> {
        Opcode<FieldInstruction> opcode = Opcode.require(input);
        return new FieldInstruction(opcode, input.readUnsignedShort());
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        output.writeShort(value.getFieldIndex());
    }, Skip.exact(3));
    private final int fieldIndex;

    /**
     * @param opcode      Opcode.
     * @param fieldIndex Constant pool entry index.
     */
    public FieldInstruction(Opcode<FieldInstruction> opcode, int fieldIndex) {
        super(opcode);
        this.fieldIndex = fieldIndex;
    }

    /**
     * @return Constant pool entry index.
     */
    public int getFieldIndex() {
        return fieldIndex;
    }
}
