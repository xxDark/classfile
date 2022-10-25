package dev.xdark.classfile.opcode;

import dev.xdark.classfile.attribute.code.Label;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Flow control instruction.
 *
 * @author xDark
 */
public interface FlowInstruction {

    /**
     * @return A list of targets.
     */
    @NotNull List<Label> getTargets();
}
