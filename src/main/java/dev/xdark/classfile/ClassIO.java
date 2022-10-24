package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeIO;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.constantpool.*;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.method.MethodVisitor;
import dev.xdark.classfile.io.Input;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class IO.
 *
 * @author xDark
 */
public class ClassIO {

    /**
     * Reads class file and feeds the data
     * into  the visitor.
     *
     * @param input        Input to read the class from.
     * @param classVisitor Class visitor.
     * @throws IOException Throws I/O exception if any error occurs.
     */
    public static void read(@NotNull Input input, @NotNull ClassVisitor classVisitor) throws IOException {
        int magic = input.readInt();
        if (magic != 0xcafebabe) {
            throw new IOException("Bad classfile magic " + Integer.toHexString(magic));
        }
        int minor = input.readUnsignedShort();
        int major = input.readUnsignedShort();
        ClassVersion version = ClassVersion.of(major, minor);
        if (version == null) {
            throw new InvalidClassException("Invalid or unsupported class version (major: " + major + ", minor: " + minor + ")");
        }
        int constantPoolCount = input.readUnsignedShort();
        List<ConstantEntry<?>> entries = new ArrayList<>(constantPoolCount + 1);
        entries.add(null);
        for (int i = 1; i < constantPoolCount; i++) {
            int tagId = input.readUnsignedByte();
            Tag<?> tag = Tag.of(tagId);
            if (tag == null) {
                throw new InvalidClassException("Unknown tag " + tagId);
            }
            ConstantEntry<?> entry = tag.codec().read(input);
            entries.add(entry);
            if (tag.size() == 2) {
                entries.add(null);
            }
        }
        ConstantPool constantPool = new BuiltConstantPool(entries);
        {
            AccessFlag flag = AccessFlag.flag(input.readUnsignedShort());
            int thisClass = input.readUnsignedShort();
            int superClass = input.readUnsignedShort();
            int[] interfaces = new int[input.readUnsignedShort()];
            for (int i = 0, j = interfaces.length; i < j; i++) {
                interfaces[i] = input.readUnsignedShort();
            }
            classVisitor.visit(version, constantPool, flag, thisClass, superClass, interfaces);
        }
        ClassContext classContext = new ClassContext(version, constantPool);
        for (int i = 0, j = input.readUnsignedShort(); i < j; i++) {
            AccessFlag flag = AccessFlag.flag(input.readUnsignedShort());
            int nameIndex = input.readUnsignedShort();
            int descriptorIndex = input.readUnsignedShort();
            FieldVisitor fieldVisitor = classVisitor.visitField(flag, nameIndex, descriptorIndex);
            AttributeVisitor attributeVisitor;
            if (fieldVisitor == null || (attributeVisitor = fieldVisitor.visitAttributes()) == null) {
                AttributeIO.skip(input);
            } else {
                AttributeIO.read(input, classContext, attributeVisitor);
            }
        }
        for (int i = 0, j = input.readUnsignedShort(); i < j; i++) {
            AccessFlag flag = AccessFlag.flag(input.readUnsignedShort());
            int nameIndex = input.readUnsignedShort();
            int descriptorIndex = input.readUnsignedShort();
            MethodVisitor methodVisitor = classVisitor.visitMethod(flag, nameIndex, descriptorIndex);
            AttributeVisitor attributeVisitor;
            if (methodVisitor == null || (attributeVisitor = methodVisitor.visitAttributes()) == null) {
                AttributeIO.skip(input);
            } else {
                AttributeIO.read(input, classContext, attributeVisitor);
            }
        }
        AttributeVisitor attributeVisitor = classVisitor.visitAttributes();
        if (attributeVisitor == null) {
            AttributeIO.skip(input);
        } else {
            AttributeIO.read(input, classContext, attributeVisitor);
        }
        classVisitor.visitEnd();
    }

    /**
     * Reads class name from {@link Tag#CONSTANT_Class}.
     *
     * @param constantPool Constant pool.
     * @param classIndex   Class index.
     * @return Class name.
     * @throws IllegalArgumentException if {@code classIndex} doesn't point
     *                                  to {@link Tag#CONSTANT_Class}.
     */
    public static String getClassName(ConstantPool constantPool, int classIndex) {
        ConstantClass constantClass = constantPool.get(classIndex, Tag.CONSTANT_Class);
        return constantPool.get(constantClass.index(), Tag.CONSTANT_Utf8).value();
    }
}
