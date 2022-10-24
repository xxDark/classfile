package dev.xdark.classfile.pseuo;

import dev.xdark.classfile.opcode.JvmOpcodes;

/**
 * Type node.
 * Supported opcodes:
 * {@link JvmOpcodes#NEW}, {@link JvmOpcodes#CHECKCAST},
 * {@link JvmOpcodes#INSTANCEOF}, {@link JvmOpcodes#ANEWARRAY}.
 *
 * @author xDark
 */
public final class TypeInstructionNode extends InstructionNode {
    public String type;

    /**
     * @param opcode Instruction opcode.
     * @param type   Type descriptor.
     */
    public TypeInstructionNode(int opcode, String type) {
        super(verify(opcode, opcode == JvmOpcodes.NEW || opcode == JvmOpcodes.CHECKCAST || opcode == JvmOpcodes.INSTANCEOF || opcode == JvmOpcodes.ANEWARRAY));
        this.type = type;
    }
}
