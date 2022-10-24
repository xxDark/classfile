package dev.xdark.classfile.pseuo;

import dev.xdark.classfile.opcode.JvmOpcodes;

/**
 * Local variable increment instruction.
 *
 * @author xDark
 */
public final class IncrementInstructionNode extends InstructionNode {
    public int variableIndex;
    public int increment;

    /**
     * @param variableIndex Variable index.
     * @param increment     Increment amount.
     */
    public IncrementInstructionNode(int variableIndex, int increment) {
        super(JvmOpcodes.IINC);
        this.variableIndex = variableIndex;
        this.increment = increment;
    }
}
