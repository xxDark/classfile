package dev.xdark.classfile.opcode;

import org.jetbrains.annotations.NotNull;

/**
 * Instruction.
 *
 * @author xDark
 */
public interface Instruction<SELF extends Instruction<SELF>> {

    /**
     * @return JVM opcode.
     */
    @NotNull Opcode<SELF> getOpcode();
}
