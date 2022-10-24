package dev.xdark.classfile.attribute;

import dev.xdark.classfile.ClassContext;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.constantpool.Tag;
import dev.xdark.classfile.io.ContextEncoder;
import dev.xdark.classfile.io.Input;
import dev.xdark.classfile.io.Output;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Attribute IO.
 *
 * @author xDark
 */
public final class AttributeIO {

    public AttributeIO() {
    }

    /**
     * @param input Skips attributes.
     */
    public static void skip(@NotNull Input input) throws IOException {
        for (int i = 0, j = input.readUnsignedShort(); i < j; i++) {
            input.skipBytes(2);
            input.skipBytes(input.readInt());
        }
    }

    /**
     * Reads attributes.
     *
     * @param input        Input to read attributes from.
     * @param classContext Class context.
     * @param visitor      Attribute visitor.
     * @throws IOException If any I/O error occurs.
     */
    public static void read(@NotNull Input input, @NotNull ClassContext classContext, @NotNull AttributeVisitor visitor) throws IOException {
        visitor.visitAttributes();
        ConstantPool constantPool = classContext.getConstantPool();
        for (int i = 0, j = input.readUnsignedShort(); i < j; i++) {
            int nameIndex = input.readUnsignedShort();
            int position = input.position();
            AttributeInfo<?> info = AttributeInfo.byName(constantPool.get(nameIndex, Tag.CONSTANT_Utf8).value());
            Attribute<?> attribute;
            try {
                attribute = info.codec().read(input, classContext);
            } catch (InvalidAttributeException ex) {
                input.position(position);
                int length = input.readInt();
                attribute = new UnknownAttribute(input.read(length));
            }
            visitor.visitAttribute(nameIndex, attribute);
        }
        visitor.visitEnd();
    }

    /**
     * Writes an attribute.
     *
     * @param output       Output to write attribute to.
     * @param nameIndex    Attribute name index.
     * @param classContext Class context.
     * @param attribute    Attribute to write.
     * @throws IOException If any I/O error occurs.
     */
    public static void write(@NotNull Output output, int nameIndex, @NotNull ClassContext classContext, @NotNull Attribute<?> attribute) throws IOException {
        output.writeShort(nameIndex);
        ((ContextEncoder<Attribute, ClassContext>) attribute.info().codec()).write(output, attribute, classContext);
    }
}
