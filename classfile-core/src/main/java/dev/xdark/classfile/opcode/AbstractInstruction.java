package dev.xdark.classfile.opcode;

import org.jetbrains.annotations.NotNull;

/**
 * Basic instruction.
 *
 * @author xDark
 */
public abstract class AbstractInstruction<SELF extends AbstractInstruction<SELF>> implements Instruction<SELF> {
    private final Opcode<SELF> opcode;

    /**
     * @param opcode Opcode.
     */
    protected AbstractInstruction(Opcode<SELF> opcode) {
        this.opcode = opcode;
    }

    @Override
    public final @NotNull Opcode<SELF> getOpcode() {
        return opcode;
    }
}
