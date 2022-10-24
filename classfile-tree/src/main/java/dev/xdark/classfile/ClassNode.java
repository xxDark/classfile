package dev.xdark.classfile;

import dev.xdark.classfile.attribute.*;
import dev.xdark.classfile.constantpool.*;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.method.MethodVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public final List<UnknownStoredAttribute> attributes = new ArrayList<>();

    @Override
    public void visitClass() {
    }

    @Override
    public ConstantPoolVisitor visitConstantPool() {
        ConstantPoolBuilder builder = new ConstantPoolBuilder();
        return new FilterConstantPoolVisitor(builder) {
            @Override
            public void visitEnd() {
                constantPool = builder.build();
            }
        };
    }

    @Override
    public void visit(@NotNull ClassVersion version, @NotNull AccessFlag access, int thisClass, int superClass, int[] interfaces) {
        this.version = version;
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
    public @NotNull AttributeVisitor visitAttributes() {
        return new FilterAttributeVisitor(new UnknownAttributeCollector(Objects.requireNonNull(constantPool), attributes)) {
            @Override
            public void visitAttribute(int nameIndex, @NotNull Attribute<?> attribute) {
                if (attribute instanceof SignatureAttribute) {
                    signature = constantPool.get(((SignatureAttribute) attribute).getIndex(), Tag.CONSTANT_Utf8).value();
                    return;
                }
                super.visitAttribute(nameIndex, attribute);
            }
        };
    }

    @Override
    public void visitEnd() {
    }

    public void accept(ClassVisitor classVisitor) {
        classVisitor.visitClass();
        ConstantPoolVisitor visitor = classVisitor.visitConstantPool();
        ConstantPoolBuilder builder = new ConstantPoolBuilder();
        int thisClas = builder.putClass(name);
        String superName = this.superName;
        int superClass = superName == null ? 0 : builder.putClass(superName);
        int[] interfaces = this.interfaces.stream().mapToInt(builder::putClass).toArray();
        classVisitor.visit(version, access, thisClas, superClass, interfaces);
        for (MethodNode method : methods) {
            MethodVisitor mv = classVisitor.visitMethod(method.access, builder.putUtf8(name), builder.putUtf8(method.desc));
            if (mv != null) {
                method.accept(mv, builder);
            }
        }
        for (FieldNode field : fields) {
            FieldVisitor fv = classVisitor.visitField(field.access, builder.putUtf8(name), builder.putUtf8(field.desc));
            if (fv != null) {
                field.accept(fv, builder);
            }
        }
        AttributeVisitor attributeVisitor = classVisitor.visitAttributes();
        if (attributeVisitor != null) {
            attributeVisitor.visitAttributes();
            NodeUtil.putSignature(attributeVisitor, builder, signature);
            List<UnknownStoredAttribute> attributes = this.attributes;
            for (UnknownStoredAttribute attribute : attributes) {
                attributeVisitor.visitAttribute(builder.putUtf8(attribute.getName()), attribute.getAttribute());
            }
            attributeVisitor.visitEnd();
        }
        if (visitor != null) {
            for (ConstantEntry<?> entry : builder.build()) {
                visitor.visitConstant(entry);
            }
        }
    }
}
