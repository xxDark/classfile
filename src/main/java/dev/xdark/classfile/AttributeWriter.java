package dev.xdark.classfile;

import dev.xdark.classfile.attribute.Attribute;
import dev.xdark.classfile.attribute.AttributeIO;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.io.Output;

import java.io.IOException;
import java.io.UncheckedIOException;

final class AttributeWriter implements AttributeVisitor {
    private final Output output;
    private final ClassContext classContext;
    private int count;
    private int position;

    AttributeWriter(Output output, ClassContext classContext) {
        this.output = output;
        this.classContext = classContext;
    }

    @Override
    public void visitAttributes() {
        position = output.position();
        try {
            output.writeShort(0);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void visitAttribute(int nameIndex, Attribute<?> attribute) {
        try {
            AttributeIO.write(output, nameIndex, classContext, attribute);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        count++;
    }

    @Override
    public void visitEnd() {
        try {
            Output output = this.output;
            int position = output.position();
            output.position(this.position);
            output.writeShort(count);
            output.position(position);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
