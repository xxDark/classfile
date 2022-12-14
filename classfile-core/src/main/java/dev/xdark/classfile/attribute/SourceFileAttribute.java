package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

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
    }, Skip.u32());

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
    public @NotNull AttributeInfo<SourceFileAttribute> info() {
        return AttributeInfo.SourceFile;
    }
}
