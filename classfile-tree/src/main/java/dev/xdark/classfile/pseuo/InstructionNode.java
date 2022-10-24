package dev.xdark.classfile.pseuo;

import dev.xdark.classfile.opcode.Opcode;

/**
 * Instruction node.
 *
 * @author xDark
 */
public class InstructionNode {
    private final int opcode;

    /**
     * @param opcode Instruction opcode.
     */
    public InstructionNode(int opcode) {
        this.opcode = opcode;
    }

    /**
     * @return Instruction opcode.
     */
    public final int getOpcode() {
        return opcode;
    }

    protected static int verifyRange(int from, int opcode, int to) {
        return verify(opcode, opcode >= from && opcode <= to);
    }

    protected static int verify(int opcode, boolean state) {
        if (!state) {
            Opcode<?> maybe = Opcode.of(opcode);
            throw new IllegalArgumentException("Bad opcode (" + (maybe == null ? "<unknown>" : maybe.name()) + ')');
        }
        return opcode;
    }
}
