package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeCollector;
import dev.xdark.classfile.attribute.AttributeIO;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.attribute.NamedAttributeInstance;
import dev.xdark.classfile.io.Output;
import dev.xdark.classfile.method.MethodVisitor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class MethodWriter implements MethodVisitor {
    private final List<NamedAttributeInstance<?>> attributes = new ArrayList<>();
    private AccessFlag access;
    private int nameIndex;
    private int descriptorIndex;

    @Override
    public void visit(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        this.access = access;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    @Override
    public @NotNull AttributeVisitor visitAttributes() {
        return new AttributeCollector(attributes);
    }

    @Override
    public void visitEnd() {
    }

    void writeTo(Output output, ClassContext classContext) throws IOException {
        output.writeShort(access.mask());
        output.writeShort(nameIndex);
        output.writeShort(descriptorIndex);
        AttributeIO.write(output, attributes, classContext);
    }
}
