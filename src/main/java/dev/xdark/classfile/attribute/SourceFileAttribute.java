package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;

/**
 * SourceFile.
 *
 * @author xDark
 */
public final class SourceFileAttribute implements Attribute<SourceFileAttribute> {
    static final Codec<SourceFileAttribute> CODEC = Codec.of(input -> {
        if (input.readInt() != 2) {
            throw new InvalidAttributeException("Attribute length is not 2");
        }
        return new SourceFileAttribute(input.readUnsignedShort());
    }, (output, value) -> {
        output.writeInt(2);
        output.writeShort(value.sourceFileIndex());
    });

    private final int sourceFileIndex;

    /**
     * @param sourceFileIndex Source file index.
     */
    public SourceFileAttribute(int sourceFileIndex) {
        this.sourceFileIndex = sourceFileIndex;
    }

    /**
     * @return SourceFile content index.
     */
    public int sourceFileIndex() {
        return sourceFileIndex;
    }

    @Override
    public AttributeInfo<SourceFileAttribute> info() {
        return AttributeInfo.SourceFile;
    }
}
