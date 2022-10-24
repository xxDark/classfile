package dev.xdark.classfile;

import dev.xdark.classfile.attribute.*;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.constantpool.Tag;
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
    /**
     * Methods.
     */
    public final List<MethodNode> methods = new ArrayList<>();
    /**
     * Class signature.
     */
    public String signature;
    /**
     * Class attributes.
     */
    public final List<NamedAttributeInstance<?>> attributes = new ArrayList<>();

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
        MethodNode methodNode = new MethodNode(constantPool);
        methodNode.visit(access, nameIndex, descriptorIndex);
        methods.add(methodNode);
        return methodNode;
    }

    @Override
    public @Nullable AttributeVisitor visitAttributes() {
        return new FilterAttributeVisitor(new AttributeCollector(attributes)) {
            @Override
            public void visitAttribute(int nameIndex, @NotNull Attribute<?> attribute) {
                if (attribute instanceof SignatureAttribute) {
                    signature = constantPool.get(((SignatureAttribute) attribute).getIndex(), Tag.CONSTANT_Utf8).value();
                }
                super.visitAttribute(nameIndex, attribute);
            }
        };
    }

    @Override
    public void visitEnd() {
    }
}
