package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.constantpool.ConstantEntry;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.constantpool.Tag;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.method.MethodVisitor;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Output;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Class writer.
 *
 * @author xDark
 */
public final class ClassWriter implements ClassVisitor {

    private final Output output;
    private ClassContext classContext;
    private int fieldPosition, methodPosition = -1;
    private int fieldCount, methodCount;

    public ClassWriter(Output output) {
        this.output = output;
    }

    @Override
    public void visit(@NotNull ClassVersion version, @NotNull ConstantPool constantPool, @NotNull AccessFlag access, int thisClass, int superClass, int[] interfaces) {
        try {
            Output output = this.output;
            output.writeInt(0xcafebabe);
            output.writeShort(version.minorVersion());
            output.writeShort(version.majorVersion());
            output.writeShort(constantPool.size() + 1);
            for (ConstantEntry<?> entry : constantPool) {
                if (entry != null) {
                    try {
                        Tag<?> tag = entry.tag();
                        output.writeByte(tag.id());
                        ((Codec<ConstantEntry>) tag.codec()).write(output, entry);
                    } catch (IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                }
            }
            output.writeShort(access.mask());
            output.writeShort(thisClass);
            output.writeShort(superClass);
            output.writeShort(interfaces.length);
            for (int index : interfaces) {
                output.writeShort(index);
            }
            fieldPosition = output.position();
            output.writeShort(0);
            classContext = new ClassContext(version, constantPool);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public FieldVisitor visitField(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        FieldWriter writer = new FieldWriter(output, classContext);
        writer.visit(access, nameIndex, descriptorIndex);
        fieldCount++;
        return writer;
    }

    @Override
    public MethodVisitor visitMethod(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        Output output = this.output;
        if (methodPosition == -1) {
            methodPosition = output.position();
            try {
                output.writeShort(0);
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
        MethodWriter writer = new MethodWriter(output, classContext);
        writer.visit(access, nameIndex, descriptorIndex);
        methodCount++;
        return writer;
    }

    @Override
    public AttributeVisitor visitAttributes() {
        if (methodPosition == -1) {
            methodPosition = output.position();
        }
        return new AttributeWriter(output, classContext);
    }

    @Override
    public void visitEnd() {
        Output output = this.output;
        int position = output.position();
        try {
            output.position(fieldPosition);
            output.writeShort(fieldCount);
            output.position(methodPosition);
            output.writeShort(methodCount);
            output.position(position);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
