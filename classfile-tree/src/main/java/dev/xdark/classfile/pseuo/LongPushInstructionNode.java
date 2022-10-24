package dev.xdark.classfile.pseuo;

import dev.xdark.classfile.opcode.JvmOpcodes;

/**
 * Long constant instruction.
 * Same as {@link LdcInstructionNode}, just
 * avoids auto-boxing.
 *
 * @author xDark
 * @see LdcInstructionNode
 */
public final class LongPushInstructionNode extends InstructionNode {
    public long value;

    /**
     * @param value Constant value.
     */
    public LongPushInstructionNode(long value) {
        super(JvmOpcodes.LDC);
        this.value = value;
    }
}
