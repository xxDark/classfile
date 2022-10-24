package dev.xdark.classfile;

import dev.xdark.classfile.attribute.Attribute;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.constantpool.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

final class UnknownAttributeCollector implements AttributeVisitor {
    private final ConstantPool constantPool;
    private final List<UnknownStoredAttribute> attributes;

    UnknownAttributeCollector(ConstantPool constantPool, List<UnknownStoredAttribute> attributes) {
        this.constantPool = constantPool;
        this.attributes = attributes;
    }

    @Override
    public void visitAttributes() {
    }

    @Override
    public void visitAttribute(int nameIndex, @NotNull Attribute<?> attribute) {
        attributes.add(new UnknownStoredAttribute(constantPool.get(nameIndex, Tag.CONSTANT_Utf8).value(), attribute));
    }

    @Override
    public void visitEnd() {
    }
}
