package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.io.Output;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UncheckedIOException;

final class FieldWriter implements FieldVisitor {
    private final Output output;
    private final ClassContext classContext;

    FieldWriter(Output output, ClassContext classContext) {
        this.output = output;
        this.classContext = classContext;
    }

    @Override
    public void visit(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        Output output = this.output;
        try {
            output.writeShort(access.mask());
            output.writeShort(nameIndex);
            output.writeShort(descriptorIndex);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public AttributeVisitor visitAttributes() {
        return new AttributeWriter(output, classContext);
    }

    @Override
    public void visitEnd() {
    }
}
