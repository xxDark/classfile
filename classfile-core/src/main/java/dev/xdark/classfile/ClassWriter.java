package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeCollector;
import dev.xdark.classfile.attribute.AttributeIO;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.attribute.NamedAttributeInstance;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.constantpool.ConstantPoolBuilder;
import dev.xdark.classfile.constantpool.ConstantPoolIO;
import dev.xdark.classfile.constantpool.ConstantPoolVisitor;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.io.Output;
import dev.xdark.classfile.method.MethodVisitor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class writer.
 *
 * @author xDark
 */
public final class ClassWriter implements ClassVisitor {
    private ClassVersion version;
    private AccessFlag access;
    private int thisClass, superClass;
    private int[] interfaces;
    private ConstantPoolBuilder builder;
    private final List<FieldWriter> fields = new ArrayList<>();
    private final List<MethodWriter> methods = new ArrayList<>();
    private final List<NamedAttributeInstance<?>> attributes = new ArrayList<>();

    public void writeTo(Output output) throws IOException {
        output.writeInt(0xcafebabe);
        ClassVersion version = this.version;
        output.writeShort(version.minorVersion());
        output.writeShort(version.majorVersion());
        ConstantPool cp = builder.build();
        ConstantPoolIO.write(output, cp);
        output.writeShort(access.mask());
        output.writeShort(thisClass);
        output.writeShort(superClass);
        output.writeShort(interfaces.length);
        for (int index : interfaces) {
            output.writeShort(index);
        }
        ClassContext classContext = new ClassContext(version, cp);
        List<FieldWriter> fields = this.fields;
        int count = fields.size();
        output.writeShort(count);
        for (int i = 0; i < count; i++) {
            fields.get(i).writeTo(output, classContext);
        }
        List<MethodWriter> methods = this.methods;
        count = methods.size();
        output.writeShort(count);
        for (int i = 0; i < count; i++) {
            methods.get(i).writeTo(output, classContext);
        }
        AttributeIO.write(output, attributes, classContext);
    }

    @Override
    public void visitClass() {
    }

    @Override
    public @NotNull ConstantPoolVisitor visitConstantPool() {
        return builder = new ConstantPoolBuilder();
    }

    @Override
    public void visit(@NotNull ClassVersion version, @NotNull AccessFlag access, int thisClass, int superClass, int[] interfaces) {
        this.version = version;
        this.access = access;
        this.thisClass = thisClass;
        this.superClass = superClass;
        this.interfaces = interfaces;
    }

    @Override
    public @NotNull FieldVisitor visitField(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        FieldWriter fieldWriter = new FieldWriter();
        fieldWriter.visit(access, nameIndex, descriptorIndex);
        fields.add(fieldWriter);
        return fieldWriter;
    }

    @Override
    public @NotNull MethodVisitor visitMethod(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        MethodWriter methodWriter = new MethodWriter();
        methodWriter.visit(access, nameIndex, descriptorIndex);
        methods.add(methodWriter);
        return methodWriter;
    }

    @Override
    public @NotNull AttributeVisitor visitAttributes() {
        return new AttributeCollector(attributes);
    }

    @Override
    public void visitEnd() {
    }
}
