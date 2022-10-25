package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.constantpool.ConstantPoolBuilder;
import dev.xdark.classfile.dynamic.MethodHandle;
import dev.xdark.classfile.opcode.*;
import org.jetbrains.annotations.NotNull;

/**
 * Code adapter.
 *
 * @author xDark
 */
public class CodeAdapter extends FilterCodeVisitor {
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
    public CodeAdapter(@NotNull CodeVisitor cv, @NotNull ConstantPoolBuilder builder) {
        super(cv);
        this.builder = builder;
    }

    /**
     * Visits instruction with no operands.
     *
     * @param opcode Instruction opcode.
     */
    public void visitInsn(Opcode<EmptyInstruction> opcode) {
        super.visitInstruction(new EmptyInstruction(opcode));
    }

    /**
     * @param opcode Invocation opcode.
     * @param owner  Method owner.
     * @param name   Method name.
     * @param desc   Method descriptor
     * @param itf    Whether the owner is an interface.
     */
    public void visitMethodInsn(Opcode<UnsignedShortInstruction> opcode, String owner, String name, String desc, boolean itf) {
        int index;
        if (itf) {
            index = builder.putMethodRef(owner, name, desc);
        } else {
            index = builder.putInterfaceMethodRef(owner, name, desc);
        }
        super.visitInstruction(new UnsignedShortInstruction(opcode, index));
    }

    /**
     * @param opcode Field opcode.
     * @param owner  Field owner.
     * @param name   Field name.
     * @param desc   Field descriptor
     */
    public void visitFieldInsn(Opcode<UnsignedShortInstruction> opcode, String owner, String name, String desc) {
        int index = builder.putFieldRef(owner, name, desc);
        super.visitInstruction(new UnsignedShortInstruction(opcode, index));
    }

    /**
     * @param opcode Jump opcode.
     * @param label  Jump target.
     */
    public void visitJumpInsn(Opcode<ShortJumpInstruction> opcode, Label label) {
        super.visitInstruction(new ShortJumpInstruction(opcode, label));
    }

    /**
     * @param dflt   Default branch.
     * @param keys   The values of the keys.
     * @param labels Branch offsets.
     * @throws IllegalStateException If the length of the keys does not match
     *                               with the length of the labels.
     */
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label... labels) {
        if (keys.length != labels.length) {
            throw new IllegalStateException("Length mismatch");
        }
        super.visitInstruction(new LookupSwitchInstruction(keys, labels, dflt));
    }

    /**
     * @param low    Minimum value.
     * @param high   Maximum value.
     * @param dflt   Default branch.
     * @param labels Branch offsets.
     * @throws IllegalStateException If the length of the keys does not match
     *                               with the length of the labels.
     */
    public void visitTableSwitchInsn(int low, int high, Label dflt, Label... labels) {
        if (high - low + 1 != labels.length) {
            throw new IllegalStateException("Length mismatch");
        }
        super.visitInstruction(new TableSwitchInstruction(low, high, dflt, labels));
    }

    /**
     * @param value Long value to push on the stack.
     */
    public void visitLongLdc(long value) {
        if (value == 0L || value == 1L) {
            visitInsn(value == 0L ? Opcode.LCONST_0 : Opcode.LCONST_1);
        } else {
            super.visitInstruction(new UnsignedShortInstruction(Opcode.LDC2_W, builder.putLong(value)));
        }
    }

    /**
     * @param value Double value to push on the stack.
     */
    public void visitDoubleLdc(double value) {
        if (value == 0.0D || value == 1.0D) {
            visitInsn(value == 0L ? Opcode.DCONST_0 : Opcode.DCONST_1);
        } else {
            super.visitInstruction(new UnsignedShortInstruction(Opcode.LDC2_W, builder.putDouble(value)));
        }
    }

    /**
     * @param value Int value to push on the stack.
     */
    public void visitIntLdc(int value) {
        if (value >= -1 && value <= 5) {
            visitInsn(ICONSTS[value + 1]);
        } else {
            visitLdc(builder.putInt(value));
        }
    }

    /**
     * @param value Float value to push on the stack.
     */
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

    /**
     * @param value String value to push on the stack.
     */
    public void visitUtf8Ldc(String value) {
        visitLdc(builder.putString(value));
    }

    /**
     * @param value MethodHandle to push on the stack.
     */
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
