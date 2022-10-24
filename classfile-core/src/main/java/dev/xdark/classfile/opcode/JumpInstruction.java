package dev.xdark.classfile.opcode;

/**
 * Jump instruction.
 *
 * @author xdark
 */
public abstract class JumpInstruction<SELF extends JumpInstruction<SELF>> extends AbstractInstruction<SELF> {
    private final int offset;

    protected JumpInstruction(Opcode<SELF> opcode, int offset) {
        super(opcode);
        this.offset = offset;
    }

    /**
     * @return Jump offset.
     */
    public int getOffset() {
        return offset;
    }
}
