package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.InvalidClassException;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Input;
import dev.xdark.classfile.io.Output;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Constant pool I/O.
 *
 * @author xDark
 */
public final class ConstantPoolIO {
    private ConstantPoolIO() {
    }

    /**
     * Reads constant pool.
     *
     * @param input   Input to read constant pool from.
     * @param visitor Constant pool visitor.
     * @throws IOException If any I/O error occurs.
     */
    public static void read(@NotNull Input input, @NotNull ConstantPoolVisitor visitor) throws IOException {
        int constantPoolCount = input.readUnsignedShort();
        visitor.visitConstants();
        for (int i = 1; i < constantPoolCount; i++) {
            int tagId = input.readUnsignedByte();
            Tag<?> tag = Tag.of(tagId);
            if (tag == null) {
                throw new InvalidClassException("Unknown tag " + tagId);
            }
            ConstantEntry<?> entry = tag.codec().read(input);
            visitor.visitConstant(entry);
            if (tag.size() == 2) {
                i++;
            }
        }
        visitor.visitEnd();
    }

    /**
     * Writes constant pool.
     *
     * @param output       Output to write constant pool to.
     * @param constantPool Constant pool.
     * @throws IOException If any I/O error occurs.
     */
    public static void write(@NotNull Output output, @NotNull ConstantPool constantPool) throws IOException {
        output.writeShort(constantPool.size() + 1);
        for (ConstantEntry<?> entry : constantPool) {
            Tag<?> tag = entry.tag();
            output.writeByte(tag.id());
            ((Codec<ConstantEntry>) tag.codec()).write(output, entry);
        }
    }
}
