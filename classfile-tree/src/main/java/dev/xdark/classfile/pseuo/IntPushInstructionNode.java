package dev.xdark.classfile.pseuo;

import dev.xdark.classfile.opcode.JvmOpcodes;

/**
 * Int constant instruction.
 * Same as {@link LdcInstructionNode}, just
 * avoids auto-boxing.
 *
 * @author xDark
 * @see LdcInstructionNode
 */
public final class IntPushInstructionNode extends InstructionNode {
    public int value;

    /**
     * @param value Constant value.
     */
    public IntPushInstructionNode(int value) {
        super(JvmOpcodes.LDC);
        this.value = value;
    }
}
