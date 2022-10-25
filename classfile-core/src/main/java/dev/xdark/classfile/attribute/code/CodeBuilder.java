package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.ClassVersion;
import dev.xdark.classfile.attribute.AttributeCollector;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.attribute.CodeAttribute;
import dev.xdark.classfile.attribute.NamedAttributeInstance;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.buffer.ByteBufferAllocator;
import dev.xdark.classfile.io.buffer.ByteBufferOutput;
import dev.xdark.classfile.opcode.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder for Code attribute.
 *
 * @author xDark
 */
public final class CodeBuilder implements CodeVisitor {
    private final ByteBufferOutput output = new ByteBufferOutput(ByteBufferAllocator.HEAP);
    private final List<CodeAttribute.TryCatchBlock> tryCatchBlocks = new ArrayList<>();
    private final List<NamedAttributeInstance<?>> attributes = new ArrayList<>();
    private final List<FlowPoint> flow = new ArrayList<>();
    private final ClassVersion version;
    private int maxStack, maxLocals;

    /**
     * @param version Version of the class for which Code attribute is being built.
     */
    public CodeBuilder(ClassVersion version) {
        this.version = version;
    }

    public CodeBuilder() {
        this(ClassVersion.V8);
    }

    /**
     * @return Creates the Code attribute.
     */
    public CodeAttribute create() {
        ByteBufferOutput output = this.output;
        ByteBuffer buffer = output.consume();
        byte[] code = new byte[buffer.remaining()];
        buffer.get(code);
        return new CodeAttribute(maxStack, maxLocals, code, tryCatchBlocks, attributes);
    }

    @Override
    public void visitCode() {
    }

    @Override
    public @NotNull AttributeVisitor visitAttributes() {
        return new AttributeCollector(attributes);
    }

    @Override
    public void visitInstruction(@NotNull Instruction<?> instruction) {
        if (instruction instanceof FlowInstruction) {
            flow.add(new FlowPoint(output.position(), (FlowInstruction) instruction));
        }
        try {
            ((Codec) instruction.getOpcode().codec()).write(output, instruction);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void visitLabel(@NotNull Label label) {
        label.setPosition(output.position());
    }

    @Override
    public void visitTryCatchBlock(CodeAttribute.@NotNull TryCatchBlock tryCatchBlock) {
        tryCatchBlocks.add(tryCatchBlock);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        ClassVersion version = this.version;
        int limit = version == ClassVersion.V0 ? 255 : 65535;
        if (maxStack > limit || maxLocals > limit) {
            throw new IllegalArgumentException(maxStack + " " + maxLocals);
        }
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
    }

    @Override
    public void visitEnd() {
        for (FlowPoint point : flow) {
            int position = point.position;
            FlowInstruction insn = point.instruction;
            if (insn instanceof JumpInstruction) {
                fixJumpInstruction(position, (JumpInstruction<?>) insn);
            } else if (insn instanceof LookupSwitchInstruction) {
                fixLookupSwitch(position, (LookupSwitchInstruction) insn);
            } else if (insn instanceof TableSwitchInstruction) {
                fixTableSwitch(position, (TableSwitchInstruction) insn);
            } else {
                throw new IllegalStateException("Don't know how to patch " + insn);
            }
        }
    }

    private void fixJumpInstruction(int position, JumpInstruction<?> instruction) {
        ByteBufferOutput output = this.output;
        int keep = output.position();
        output.position(position + 1); // Skip opcode
        // Patch jump offset
        Label label = instruction.getLabel();
        int offset = label.getPosition() - position;
        if (instruction instanceof ShortJumpInstruction) {
            output.writeShort(offset);
        } else {
            output.writeInt(offset);
        }
        output.position(keep);
    }

    private void fixLookupSwitch(int position, LookupSwitchInstruction instruction) {
        ByteBufferOutput output = this.output;
        int keep = output.position();
        output.position(position + 1); // Skip opcode
        // Patch default branch
        output.writeInt(instruction.getDefault().getPosition() - output.position());
        // Patch jump pairs
        Label[] labels = instruction.getLabels();
        for (Label label : labels) {
            // Skip key
            output.position(output.position() + 4);
            // Patch jump offset
            output.writeInt(label.getPosition() - output.position());
        }
        output.position(keep);
    }

    private void fixTableSwitch(int position, TableSwitchInstruction instruction) {
        ByteBufferOutput output = this.output;
        int keep = output.position();
        output.position(position + 1); // Skip opcode
        // Patch default branch
        output.writeInt(instruction.getDefault().getPosition() - output.position());
        // Skip low/high, length
        output.position(output.position() + 12);
        // Patch jump pairs
        Label[] labels = instruction.getLabels();
        for (Label label : labels) {
            // Patch jump offset
            output.writeInt(label.getPosition() - output.position());
        }
        output.position(keep);
    }

    private static final class FlowPoint {
        final int position;
        final FlowInstruction instruction;

        FlowPoint(int position, FlowInstruction instruction) {
            this.position = position;
            this.instruction = instruction;
        }
    }
}
