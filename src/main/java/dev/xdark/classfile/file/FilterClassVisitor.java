package dev.xdark.classfile.file;

import dev.xdark.classfile.AccessFlag;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.version.ClassVersion;

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
    public void visit(ClassVersion version, ConstantPool constantPool, AccessFlag access, int thisClass, int superClass, int[] interfaces) {
        ClassVisitor cv = this.cv;
        if (cv != null) {
            cv.visit(version, constantPool, access, thisClass, superClass, interfaces);
        }
    }

    @Override
    public FieldVisitor visitField(AccessFlag access, int nameIndex, int descriptorIndex) {
        ClassVisitor cv = this.cv;
        if (cv != null) {
            return cv.visitField(access, nameIndex, descriptorIndex);
        }
        return null;
    }

    @Override
    public MethodVisitor visitMethod(AccessFlag access, int nameIndex, int descriptorIndex) {
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
