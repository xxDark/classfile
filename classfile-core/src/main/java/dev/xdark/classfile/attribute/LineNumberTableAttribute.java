package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * LineNumberTable.
 *
 * @author xDark
 */
public final class LineNumberTableAttribute implements Attribute<LineNumberTableAttribute> {
    static final Codec<LineNumberTableAttribute> CODEC = Codec.of(input -> {
        int length = input.readInt();
        if (length < 2) {
            throw new InvalidAttributeException("Length is less than 2");
        }
        int count = input.readUnsignedShort();
        if (length != (count + 1) * 2) {
            throw new InvalidAttributeException("Invalid attribute content");
        }
        return new LineNumberTableAttribute(AttributeUtil.readList(input, count, LineNumber.CODEC));
    }, (output, value) -> {
        List<LineNumber> lineNumbers = value.getLineNumbers();
        output.writeInt((lineNumbers.size() + 1) * 2);
        AttributeUtil.writeList(output, lineNumbers, LineNumber.CODEC);
    }, Skip.u32());
    private final List<LineNumber> lineNumbers;

    /**
     * @param lineNumbers Line numbers.
     */
    public LineNumberTableAttribute(List<LineNumber> lineNumbers) {
        this.lineNumbers = lineNumbers;
    }

    /**
     * @return Line numbers.
     */
    public List<LineNumber> getLineNumbers() {
        return lineNumbers;
    }

    @Override
    public @NotNull AttributeInfo<LineNumberTableAttribute> info() {
        return AttributeInfo.LineNumberTable;
    }

    public static final class LineNumber {
        public static final Codec<LineNumber> CODEC = Codec.of(input -> {
            return new LineNumber(input.readUnsignedShort(), input.readUnsignedShort());
        }, (output, value) -> {
            output.writeShort(value.getInstruction());
            output.writeShort(value.getLine());
        }, Skip.exact(4));
        private final int instruction;
        private final int line;

        /**
         * @param instruction Start pc.
         * @param line        Line number.
         */
        public LineNumber(int instruction, int line) {
            this.instruction = instruction;
            this.line = line;
        }

        /**
         * @return Start pc.
         */
        public int getInstruction() {
            return instruction;
        }

        /**
         * @return Line number.
         */
        public int getLine() {
            return line;
        }
    }
}
