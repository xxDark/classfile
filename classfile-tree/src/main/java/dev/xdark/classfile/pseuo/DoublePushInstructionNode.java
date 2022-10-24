package dev.xdark.classfile.pseuo;

import dev.xdark.classfile.opcode.JvmOpcodes;

/**
 * Double constant instruction.
 * Same as {@link LdcInstructionNode}, just
 * avoids auto-boxing.
 *
 * @author xDark
 * @see LdcInstructionNode
 */
public final class DoublePushInstructionNode extends InstructionNode {
    public double value;

    /**
     * @param value Constant value.
     */
    public DoublePushInstructionNode(double value) {
        super(JvmOpcodes.LDC);
        this.value = value;
    }
}
