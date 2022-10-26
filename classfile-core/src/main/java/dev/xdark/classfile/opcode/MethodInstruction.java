package dev.xdark.classfile.opcode;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

/**
 * Method call instruction.
 *
 * @author xDark
 */
public final class MethodInstruction extends AbstractInstruction<MethodInstruction> {
    static final Codec<MethodInstruction> CODEC = Codec.of(input -> {
        Opcode<MethodInstruction> opcode = Opcode.require(input);
        return new MethodInstruction(opcode, input.readUnsignedShort());
    }, (output, value) -> {
        output.writeByte(value.getOpcode().opcode());
        output.writeShort(value.getMethodIndex());
    }, Skip.exact(3));
    private final int methodIndex;

    /**
     * @param opcode      Opcode.
     * @param methodIndex Constant pool entry index.
     */
    public MethodInstruction(Opcode<MethodInstruction> opcode, int methodIndex) {
        super(opcode);
        this.methodIndex = methodIndex;
    }

    /**
     * @return Constant pool entry index.
     */
    public int getMethodIndex() {
        return methodIndex;
    }
}
