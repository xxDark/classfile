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

    /**
     * Feeds this class node into the class visitor.
     *
     * @param classVisitor Class visitor.
     * @param builder      Constant pool builder.
     */
    public void accept(ClassVisitor classVisitor, ConstantPoolBuilder builder) {
        String superName = this.superName;
        classVisitor.visit(version, access, builder.putClass(name), superName == null ? 0 : builder.putClass(superName), interfaces.stream().mapToInt(builder::putClass).toArray());
        for (MethodNode methodNode : methods) {
            MethodVisitor mv = classVisitor.visitMethod(methodNode.access, builder.putUtf8(methodNode.name), builder.putUtf8(methodNode.desc));
            if (mv != null) {
                methodNode.accept(mv, builder);
                mv.visitEnd();
            }
        }
        for (FieldNode fieldNode : fields) {
            FieldVisitor fv = classVisitor.visitField(fieldNode.access, builder.putUtf8(fieldNode.name), builder.putUtf8(fieldNode.desc));
            if (fv != null) {
                fieldNode.accept(fv, builder);
                fv.visitEnd();
            }
        }
        AttributeVisitor attributeVisitor = classVisitor.visitAttributes();
        if (attributeVisitor != null) {
            AttributeAdapter adapter = new AttributeAdapter(attributeVisitor, builder);
            NodeUtil.putSignature(adapter, builder, signature);
            for (UnknownStoredAttribute attribute : attributes) {
                adapter.visitAttribute(attribute.getName(), attribute.getAttribute());
            }
            adapter.visitEnd();
        }
    }
}
