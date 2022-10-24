package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;

import java.util.List;

/**
 * LocalVariableTypeTable.
 *
 * @author xDark
 */
public final class LocalVariableTypeTableAttribute implements Attribute<LocalVariableTypeTableAttribute> {
    static final Codec<LocalVariableTypeTableAttribute> CODEC = Codec.of(input -> {
        int length = input.readInt();
        if (length < 2) {
            throw new InvalidAttributeException("Length is less than 2");
        }
        int count = input.readUnsignedShort();
        if (length != 2 + count * 10) {
            throw new InvalidAttributeException("Invalid attribute content");
        }
        return new LocalVariableTypeTableAttribute(AttributeUtil.readList(input, count, LocalVariableType.CODEC));
    }, (output, value) -> {
        List<LocalVariableType> localVariables = value.getLocalVariableTypes();
        output.writeInt(2 + localVariables.size() * 10);
        AttributeUtil.writeList(output, localVariables, LocalVariableType.CODEC);
    });
    private final List<LocalVariableType> localVariableTypes;

    /**
     * @param localVariableTypes List of local variable types.
     */
    public LocalVariableTypeTableAttribute(List<LocalVariableType> localVariableTypes) {
        this.localVariableTypes = localVariableTypes;
    }

    /**
     * @return Local variable types.
     */
    public List<LocalVariableType> getLocalVariableTypes() {
        return localVariableTypes;
    }

    @Override
    public AttributeInfo<LocalVariableTypeTableAttribute> info() {
        return AttributeInfo.LocalVariableTypeTable;
    }

    public static final class LocalVariableType {
        static final Codec<LocalVariableType> CODEC = Codec.of(input -> {
            return new LocalVariableType(input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort());
        }, (output, value) -> {
            output.writeShort(value.getInstruction());
            output.writeShort(value.getLength());
            output.writeShort(value.getNameIndex());
            output.writeShort(value.getSignatureIndex());
            output.writeShort(value.getIndex());
        });
        private final int instruction;
        private final int length;
        private final int nameIndex;
        private final int signatureIndex;
        private final int index;

        /**
         * @param instruction    Start pc.
         * @param length         Length to the end pc. (Start + length).
         * @param nameIndex      Name index.
         * @param signatureIndex Signature index.
         * @param index          Variable index.
         */
        public LocalVariableType(int instruction, int length, int nameIndex, int signatureIndex, int index) {
            this.instruction = instruction;
            this.length = length;
            this.nameIndex = nameIndex;
            this.signatureIndex = signatureIndex;
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
         * @return Signature index.
         */
        public int getSignatureIndex() {
            return signatureIndex;
        }

        /**
         * @return Variable index.
         */
        public int getIndex() {
            return index;
        }
    }
}
