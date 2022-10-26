package dev.xdark.classfile.attribute.code;

import dev.xdark.classfile.dynamic.MethodHandle;
import dev.xdark.classfile.opcode.*;

/**
 * Code adapter for read and write operations.
 *
 * @author xDark
 * @see CodeWriter
 */
public interface CodeAdapter extends CodeVisitor {

    /**
     * Visits instruction with no operands.
     *
     * @param opcode Instruction opcode.
     */
    void visitInsn(Opcode<EmptyInstruction> opcode);

    /**
     * @param opcode Invocation opcode.
     * @param owner  Method owner.
     * @param name   Method name.
     * @param desc   Method descriptor
     * @param itf    Whether the owner is an interface.
     */
    void visitMethodInsn(Opcode<MethodInstruction> opcode, String owner, String name, String desc, boolean itf);

    /**
     * @param opcode Field opcode.
     * @param owner  Field owner.
     * @param name   Field name.
     * @param desc   Field descriptor
     */
    void visitFieldInsn(Opcode<FieldInstruction> opcode, String owner, String name, String desc);

    /**
     * @param opcode Jump opcode.
     * @param label  Jump target.
     */
    void visitWideJumpInsn(Opcode<IntJumpInstruction> opcode, Label label);

    /**
     * @param opcode Jump opcode.
     * @param label  Jump target.
     */
    void visitJumpInsn(Opcode<ShortJumpInstruction> opcode, Label label);

    /**
     * @param dflt   Default branch.
     * @param keys   The values of the keys.
     * @param labels Branch offsets.
     * @throws IllegalStateException If the length of the keys does not match
     *                               with the length of the labels.
     */
    void visitLookupSwitchInsn(Label dflt, int[] keys, Label... labels);

    /**
     * @param low    Minimum value.
     * @param high   Maximum value.
     * @param dflt   Default branch.
     * @param labels Branch offsets.
     * @throws IllegalStateException If the length of the keys does not match
     *                               with the length of the labels.
     */
    void visitTableSwitchInsn(int low, int high, Label dflt, Label... labels);

    /**
     * @param value Long value to push on the stack.
     */
    void visitLongLdc(long value);

    /**
     * @param value Double value to push on the stack.
     */
    void visitDoubleLdc(double value);

    /**
     * @param value Int value to push on the stack.
     */
    void visitIntLdc(int value);

    /**
     * @param value Float value to push on the stack.
     */
    void visitFloatLdc(float value);

    /**
     * @param value String value to push on the stack.
     */
    void visitUtf8Ldc(String value);

    /**
     * @param value MethodHandle to push on the stack.
     */
    void visitMethodHandleLdc(MethodHandle value);
}
