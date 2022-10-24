package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.constantpool.ConstantPoolVisitor;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.method.MethodVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class visitor that forwards to another visitor.
 *
 * @author xDark
 */
public class FilterClassVisitor implements ClassVisitor {
    @Nullable
    protected ClassVisitor cv;

    public FilterClassVisitor(@Nullable ClassVisitor cv) {
        this.cv = cv;
    }

    public FilterClassVisitor() {
    }

    @Override
    public void visitClass() {
        ClassVisitor cv = this.cv;
        if (cv != null) {
            cv.visitClass();
        }
    }

    @Override
    public ConstantPoolVisitor visitConstantPool() {
        ClassVisitor cv = this.cv;
        return cv == null ? null : cv.visitConstantPool();
    }

    @Override
    public void visit(@NotNull ClassVersion version, @NotNull AccessFlag access, int thisClass, int superClass, int[] interfaces) {
        ClassVisitor cv = this.cv;
        if (cv != null) {
            cv.visit(version, access, thisClass, superClass, interfaces);
        }
    }

    @Override
    public FieldVisitor visitField(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        ClassVisitor cv = this.cv;
        if (cv != null) {
            return cv.visitField(access, nameIndex, descriptorIndex);
        }
        return null;
    }

    @Override
    public MethodVisitor visitMethod(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        ClassVisitor cv = this.cv;
        if (cv != null) {
            return cv.visitMethod(access, nameIndex, descriptorIndex);
        }
        return null;
    }

    @Override
    public AttributeVisitor visitAttributes() {
        ClassVisitor cv = this.cv;
        return cv == null ? null : cv.visitAttributes();
    }

    @Override
    public void visitEnd() {
        ClassVisitor cv = this.cv;
        if (cv != null) {
            cv.visitEnd();
        }
    }
}
