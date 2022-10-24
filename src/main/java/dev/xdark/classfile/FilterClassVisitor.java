package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.method.MethodVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * Class visitor that forwards to another visitor.
 *
 * @author xDark
 */
public class FilterClassVisitor implements ClassVisitor {
    protected ClassVisitor cv;

    public FilterClassVisitor(ClassVisitor cv) {
        this.cv = cv;
    }

    public FilterClassVisitor() {
    }

    @Override
    public void visit(@NotNull ClassVersion version, @NotNull ConstantPool constantPool, @NotNull AccessFlag access, int thisClass, int superClass, int[] interfaces) {
        ClassVisitor cv = this.cv;
        if (cv != null) {
            cv.visit(version, constantPool, access, thisClass, superClass, interfaces);
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
