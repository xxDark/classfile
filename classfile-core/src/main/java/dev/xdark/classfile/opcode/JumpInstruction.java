package dev.xdark.classfile.opcode;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Jump instruction.
 *
 * @author xdark
 */
public abstract class JumpInstruction<SELF extends JumpInstruction<SELF>>
        extends AbstractInstruction<SELF>
        implements FlowInstruction {
    private final Label label;

    protected JumpInstruction(Opcode<SELF> opcode, Label label) {
        super(opcode);
        this.label = label;
    }

    /**
     * @return Jump label.
     */
    public Label getLabel() {
        return label;
    }

    @Override
    public @NotNull List<Label> getTargets() {
        return Collections.singletonList(label);
    }
}
