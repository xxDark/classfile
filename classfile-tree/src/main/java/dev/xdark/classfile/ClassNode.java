package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.method.MethodVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class node.
 *
 * @author xDark
 */
public class ClassNode implements ClassVisitor {

    /**
     * Class version.
     */
    public ClassVersion version;
    /**
     * Constant pool.
     */
    public ConstantPool constantPool;
    /**
     * Access flags.
     */
    public AccessFlag access;
    /**
     * Class name.
     */
    public String name;
    /**
     * Super class name.
     */
    public String superName;
    /**
     * Interfaces.
     */
    public final List<String> interfaces = new ArrayList<>();
    /**
     * Fields.
     */
    public final List<FieldNode> fields = new ArrayList<>();

    @Override
    public void visit(@NotNull ClassVersion version, @NotNull ConstantPool constantPool, @NotNull AccessFlag access, int thisClass, int superClass, int[] interfaces) {
        this.version = version;
        this.constantPool = constantPool;
        this.access = access;
        // TODO make utilities
        name = ClassIO.getClassName(constantPool, thisClass).replace('/', '.');
        if (superClass != 0) {
            superName = ClassIO.getClassName(constantPool, superClass).replace('/', '.');
        }
        List<String> interfaceList = this.interfaces;
        interfaceList.clear();
        for (int iface : interfaces) {
            interfaceList.add(ClassIO.getClassName(constantPool, iface).replace('/', '.'));
        }
    }

    @Override
    public @Nullable FieldVisitor visitField(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        FieldNode fieldNode = new FieldNode(constantPool);
        fieldNode.visit(access, nameIndex, descriptorIndex);
        fields.add(fieldNode);
        return fieldNode;
    }

    @Override
    public @Nullable MethodVisitor visitMethod(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        return null;
    }

    @Override
    public @Nullable AttributeVisitor visitAttributes() {
        return null;
    }

    @Override
    public void visitEnd() {
    }
}
