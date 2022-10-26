package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.constantpool.ConstantPoolBuilder;
import dev.xdark.classfile.dynamic.MethodHandle;
import dev.xdark.classfile.opcode.*;
import org.jetbrains.annotations.NotNull;

/**
 * Code writer.
 *
 * @author xDark
 */
public class CodeWriter extends FilterCodeVisitor implements CodeAdapter {
    private static final Opcode<EmptyInstruction>[] ICONSTS = new Opcode[]{
            Opcode.ICONST_M1,
            Opcode.ICONST_0,
            Opcode.ICONST_1,
            Opcode.ICONST_2,
            Opcode.ICONST_3,
            Opcode.ICONST_4,
            Opcode.ICONST_5,
    };
    protected final ConstantPoolBuilder builder;

    /**
     * @param cv      Backing code visitor.
     * @param builder Constant pool builder.
     */
    public CodeWriter(@NotNull CodeVisitor cv, @NotNull ConstantPoolBuilder builder) {
        super(cv);
        this.builder = builder;
    }

    @Override
    public void visitInsn(Opcode<EmptyInstruction> opcode) {
        super.visitInstruction(new EmptyInstruction(opcode));
    }

    @Override
    public void visitMethodInsn(Opcode<MethodInstruction> opcode, String owner, String name, String desc, boolean itf) {
        int index;
        if (itf) {
            index = builder.putMethodRef(owner, name, desc);
        } else {
            index = builder.putInterfaceMethodRef(owner, name, desc);
        }
        super.visitInstruction(new MethodInstruction(opcode, index));
    }

    @Override
    public void visitFieldInsn(Opcode<FieldInstruction> opcode, String owner, String name, String desc) {
        int index = builder.putFieldRef(owner, name, desc);
        super.visitInstruction(new FieldInstruction(opcode, index));
    }

    @Override
    public void visitWideJumpInsn(Opcode<IntJumpInstruction> opcode, Label label) {
        super.visitInstruction(new IntJumpInstruction(opcode, label));
    }

    @Override
    public void visitJumpInsn(Opcode<ShortJumpInstruction> opcode, Label label) {
        super.visitInstruction(new ShortJumpInstruction(opcode, label));
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label... labels) {
        if (keys.length != labels.length) {
            throw new IllegalStateException("Length mismatch");
        }
        super.visitInstruction(new LookupSwitchInstruction(keys, labels, dflt));
    }

    @Override
    public void visitTableSwitchInsn(int low, int high, Label dflt, Label... labels) {
        if (high - low + 1 != labels.length) {
            throw new IllegalStateException("Length mismatch");
        }
        super.visitInstruction(new TableSwitchInstruction(low, high, dflt, labels));
    }

    @Override
    public void visitLongLdc(long value) {
        if (value == 0L || value == 1L) {
            visitInsn(value == 0L ? Opcode.LCONST_0 : Opcode.LCONST_1);
        } else {
            super.visitInstruction(new UnsignedShortInstruction(Opcode.LDC2_W, builder.putLong(value)));
        }
    }

    @Override
    public void visitDoubleLdc(double value) {
        if (value == 0.0D || value == 1.0D) {
            visitInsn(value == 0L ? Opcode.DCONST_0 : Opcode.DCONST_1);
        } else {
            super.visitInstruction(new UnsignedShortInstruction(Opcode.LDC2_W, builder.putDouble(value)));
        }
    }

    @Override
    public void visitIntLdc(int value) {
        if (value >= -1 && value <= 5) {
            visitInsn(ICONSTS[value + 1]);
        } else {
            visitLdc(builder.putInt(value));
        }
    }

    @Override
    public void visitFloatLdc(float value) {
        if (value == 0.0F) {
            visitInsn(Opcode.FCONST_0);
        } else if (value == 1.0F) {
            visitInsn(Opcode.FCONST_1);
        } else if (value == 2.0F) {
            visitInsn(Opcode.FCONST_2);
        } else {
            visitLdc(builder.putFloat(value));
        }
    }

    @Override
    public void visitUtf8Ldc(String value) {
        visitLdc(builder.putString(value));
    }

    @Override
    public void visitMethodHandleLdc(MethodHandle value) {
        visitLdc(builder.putMethodHandle(value));
    }

    private void visitLdc(int index) {
        if (index < 256) {
            super.visitInstruction(new UnsignedByteInstruction(Opcode.LDC, index));
        } else {
            super.visitInstruction(new UnsignedShortInstruction(Opcode.LDC_W, index));
        }
    }

    @Override
    public void visitInstruction(@NotNull Instruction<?> instruction) {
        throw new UnsupportedOperationException("Code adapter does not support visitInstruction calls");
    }
}
