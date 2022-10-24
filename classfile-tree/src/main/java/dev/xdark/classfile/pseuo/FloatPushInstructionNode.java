package dev.xdark.classfile.pseuo;

import dev.xdark.classfile.opcode.JvmOpcodes;

/**
 * Float constant instruction.
 * Same as {@link LdcInstructionNode}, just
 * avoids auto-boxing.
 *
 * @author xDark
 * @see LdcInstructionNode
 */
public final class FloatPushInstructionNode extends InstructionNode {
    public float value;

    /**
     * @param value Constant value.
     */
    public FloatPushInstructionNode(float value) {
        super(JvmOpcodes.LDC);
        this.value = value;
    }
}
