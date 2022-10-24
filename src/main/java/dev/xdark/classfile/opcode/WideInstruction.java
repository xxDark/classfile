package dev.xdark.classfile.opcode;

import dev.xdark.classfile.InvalidClassException;
import dev.xdark.classfile.io.Codec;

import static dev.xdark.classfile.opcode.JvmOpcodes.*;

/**
 * Wide instruction.
 *
 * @author xDark
 */
public final class WideInstruction extends AbstractInstruction<WideInstruction> {
    static final Codec<WideInstruction> CODEC = Codec.of(input -> {
        input.skipBytes(1);
        int opcode = input.readUnsignedByte();
        int operand;
        switch (opcode) {
            case ILOAD:
            case FLOAD:
            case ALOAD:
            case LLOAD:
            case DLOAD:
            case ISTORE:
            case FSTORE:
            case ASTORE:
            case LSTORE:
            case DSTORE:
            case RET:
                operand = input.readUnsignedShort();
                break;
            case IINC:
                operand = input.readInt();
                break;
            default:
                throw new InvalidClassException("Unknown wide instruction type " + opcode);
        }
        return new WideInstruction(opcode, operand);
    }, (output, value) -> {
        output.writeByte(Opcode.WIDE.opcode());
        int opcode = value.getInnerOpcode();
        output.writeByte(opcode);
        int operand = value.getOperand();
        switch (opcode) {
            case ILOAD:
            case FLOAD:
            case ALOAD:
            case LLOAD:
            case DLOAD:
            case ISTORE:
            case FSTORE:
            case ASTORE:
            case LSTORE:
            case DSTORE:
            case RET:
                output.writeShort(operand);
                break;
            case IINC:
                output.writeInt(operand);
                break;
            default:
                throw new InvalidClassException("Unknown wide instruction type " + opcode);
        }
    });
    private final int opcode;
    private final int operand;

    /**
     * @param opcode  Opcode.
     * @param operand Extended operand.
     */
    public WideInstruction(int opcode, int operand) {
        super(Opcode.WIDE);
        this.opcode = opcode;
        this.operand = operand;
    }

    /**
     * @return Opcode associated to this instruction.
     */
    public int getInnerOpcode() {
        return opcode;
    }

    /**
     * @return Extended operand.
     */
    public int getOperand() {
        return operand;
    }
}
