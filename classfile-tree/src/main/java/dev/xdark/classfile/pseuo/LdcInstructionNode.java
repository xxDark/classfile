package dev.xdark.classfile.pseuo;

import dev.xdark.classfile.opcode.JvmOpcodes;

/**
 * LDC instruction.
 *
 * @author xDark
 */
public final class LdcInstructionNode extends InstructionNode {
    public Object value;

    /**
     * @param value Constant value.
     */
    public LdcInstructionNode(Object value) {
        super(JvmOpcodes.LDC); // Actual opcode is abstracted away
        // and will be rewritten later.
        this.value = value;
    }
}
