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
 * Field node.
 *
 * @author xDark
 */
public class FieldNode implements FieldVisitor {
    /**
     * Access flags.
     */
    public AccessFlag access;
    /**
     * Field name.
     */
    public String name;
    /**
     * Field descriptor.
     */
    public String desc;
    /**
     * Field signature.
     */
    public String signature;
    /**
     * ConstantValue.
     */
    public Object constantValue;
    /**
     * Field attributes.
     */
    public final List<UnknownStoredAttribute> attributes = new ArrayList<>();
    private ConstantPool constantPool;

    /**
     * @param constantPool Constant pool.
     * @apiNote Usually only invoked by ClassNode.
     */
    public FieldNode(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public FieldNode() {
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
    public @Nullable AttributeVisitor visitAttributes() {
        return new FilterAttributeVisitor(new UnknownAttributeCollector(Objects.requireNonNull(constantPool), attributes)) {
            @Override
            public void visitAttribute(int nameIndex, @NotNull Attribute<?> attribute) {
                if (attribute instanceof SignatureAttribute) {
                    signature = constantPool.get(((SignatureAttribute) attribute).getIndex(), Tag.CONSTANT_Utf8).value();
                    return;
                } else if (attribute instanceof ConstantValueAttribute) {
                    ConstantEntry<?> entry = constantPool.get(((ConstantValueAttribute) attribute).getIndex());
                    // This is where it gets... tricky.
                    //          Field Type 	              Entry Type
                    // long 	                        CONSTANT_Long
                    // float 	                        CONSTANT_Float
                    // double 	                        CONSTANT_Double
                    // int, short, char, byte, boolean 	CONSTANT_Integer
                    // String                          	CONSTANT_String
                    // See this CONSTANT_String? :x
                    if (entry instanceof ValueEntry) {
                        if (!(entry instanceof ConstantUtf8)) {
                            constantValue = ((ValueEntry<?>) entry).getValue();
                            return;
                        }
                    } else if (entry instanceof ConstantString) {
                        constantValue = constantPool.get(((ConstantString) entry).index(), Tag.CONSTANT_Utf8).value();
                        return;
                    }
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

    public void accept(FieldVisitor fieldVisitor, ConstantPoolBuilder builder) {
        fieldVisitor.visit(access, builder.putUtf8(name), builder.putUtf8(desc));
        AttributeVisitor attributeVisitor = fieldVisitor.visitAttributes();
        if (attributeVisitor != null) {
            NodeUtil.putSignature(attributeVisitor, builder, signature);
            List<UnknownStoredAttribute> attributes = this.attributes;
            for (UnknownStoredAttribute attribute : attributes) {
                attributeVisitor.visitAttribute(builder.putUtf8(attribute.getName()), attribute.getAttribute());
            }
            attributeVisitor.visitEnd();
        }
    }
}
