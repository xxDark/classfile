package dev.xdark.classfile;

import dev.xdark.classfile.attribute.*;
import dev.xdark.classfile.attribute.code.CodeIO;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.constantpool.ConstantPoolBuilder;
import dev.xdark.classfile.constantpool.Tag;
import dev.xdark.classfile.method.MethodVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Method node.
 *
 * @author xDark
 */
public class MethodNode implements MethodVisitor {
    /**
     * Access flags.
     */
    public AccessFlag access;
    /**
     * Method name.
     */
    public String name;
    /**
     * Method descriptor.
     */
    public String desc;
    /**
     * Method signature.
     */
    public String signature;
    /**
     * Method attributes.
     */
    public final List<UnknownStoredAttribute> attributes = new ArrayList<>();
    private ConstantPool constantPool;

    /**
     * @param constantPool Constant pool.
     * @apiNote Usually only invoked by ClassNode.
     */
    public MethodNode(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public MethodNode() {
        this(null);
    }

    @Override
    public void visit(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        ConstantPool constantPool = this.constantPool;
        Objects.requireNonNull(constantPool);
        this.access = access;
        name = constantPool.get(nameIndex, Tag.CONSTANT_Utf8).value();
        desc = constantPool.get(descriptorIndex, Tag.CONSTANT_Utf8).value();
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
        constantPool = null; // This releases the reference to the ConstantPool
        // passed by ClassNode.
    }

    public void accept(MethodVisitor methodVisitor, ConstantPoolBuilder builder) {
        AttributeVisitor attributeVisitor = methodVisitor.visitAttributes();
        if (attributeVisitor != null) {
            attributeVisitor.visitAttributes();
            NodeUtil.putSignature(attributeVisitor, builder, signature);
            List<UnknownStoredAttribute> attributes = this.attributes;
            for (UnknownStoredAttribute attribute : attributes) {
                attributeVisitor.visitAttribute(builder.putUtf8(attribute.getName()), attribute.getAttribute());
            }
            attributeVisitor.visitEnd();
        }
    }
}
