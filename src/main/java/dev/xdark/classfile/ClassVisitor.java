package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.method.MethodVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class visitor.
 *
 * @author xDark
 */
public interface ClassVisitor {

    /**
     * @param version      Class version.
     * @param constantPool Constant pool.
     * @param access       Access flags.
     * @param thisClass    Class name index.
     * @param superClass   Super name index.
     * @param interfaces   Interface name indices.
     */
    void visit(@NotNull ClassVersion version, @NotNull ConstantPool constantPool, @NotNull AccessFlag access, int thisClass, int superClass, int[] interfaces);

    /**
     * Visits new field.
     *
     * @param access          Field access.
     * @param nameIndex       Field name index.
     * @param descriptorIndex Field descriptor index.
     * @return New field visitor or {@code null},
     * if field should not be visited.
     */
    @Nullable FieldVisitor visitField(@NotNull AccessFlag access, int nameIndex, int descriptorIndex);

    /**
     * Visits new method.
     *
     * @param access          Method access.
     * @param nameIndex       Method name index.
     * @param descriptorIndex Method descriptor index.
     * @return New method visitor or {@code null},
     * if method should not be visited.
     */
    @Nullable MethodVisitor visitMethod(@NotNull AccessFlag access, int nameIndex, int descriptorIndex);

    /**
     * @return New attribute visitor or {@code null},
     * if attributes should not be visited.
     */
    @Nullable AttributeVisitor visitAttributes();

    /**
     * Called after class has been visited.
     */
    void visitEnd();
}
