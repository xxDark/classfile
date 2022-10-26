package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * LocalVariableTable.
 *
 * @author xDark
 */
public final class LocalVariableTableAttribute implements Attribute<LocalVariableTableAttribute> {
    static final Codec<LocalVariableTableAttribute> CODEC = Codec.of(input -> {
        int length = input.readInt();
        if (length < 2) {
            throw new InvalidAttributeException("Length is less than 2");
        }
        int count = input.readUnsignedShort();
        if (length != 2 + count * 10) {
            throw new InvalidAttributeException("Invalid attribute content");
        }
        return new LocalVariableTableAttribute(AttributeUtil.readList(input, count, LocalVariable.CODEC));
    }, (output, value) -> {
        List<LocalVariable> localVariables = value.getLocalVariables();
        output.writeInt(2 + localVariables.size() * 10);
        AttributeUtil.writeList(output, localVariables, LocalVariable.CODEC);
    }, Skip.u32());
    private final List<LocalVariable> localVariables;

    /**
     * @param localVariables Local variables.
     */
    public LocalVariableTableAttribute(List<LocalVariable> localVariables) {
        this.localVariables = localVariables;
    }

    /**
     * @return A list of local variables.
     */
    public List<LocalVariable> getLocalVariables() {
        return localVariables;
    }

    @Override
    public @NotNull AttributeInfo<LocalVariableTableAttribute> info() {
        return AttributeInfo.LocalVariableTable;
    }

    public static final class LocalVariable {
        static final Codec<LocalVariable> CODEC = Codec.of(input -> {
            return new LocalVariable(input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort());
        }, (output, value) -> {
            output.writeShort(value.getInstruction());
            output.writeShort(value.getLength());
            output.writeShort(value.getNameIndex());
            output.writeShort(value.getDescriptorIndex());
            output.writeShort(value.getIndex());
        }, Skip.exact(10));
        private final int instruction;
        private final int length;
        private final int nameIndex;
        private final int descriptorIndex;
        private final int index;

        /**
         * @param instruction     Start pc.
         * @param length          Length to the end pc. (Start + length).
         * @param nameIndex       Name index.
         * @param descriptorIndex Descriptor index.
         * @param index           Variable index.
         */
        public LocalVariable(int instruction, int length, int nameIndex, int descriptorIndex, int index) {
            this.instruction = instruction;
            this.length = length;
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.index = index;
        }

        /**
         * @return Start pc.
         */
        public int getInstruction() {
            return instruction;
        }

        /**
         * @return Length to the end pc. (Start + length).
         */
        public int getLength() {
            return length;
        }

        /**
         * @return Name index.
         */
        public int getNameIndex() {
            return nameIndex;
        }

        /**
         * @return Descriptor index.
         */
        public int getDescriptorIndex() {
            return descriptorIndex;
        }

        /**
         * @return Variable index.
         */
        public int getIndex() {
            return index;
        }
    }
}
