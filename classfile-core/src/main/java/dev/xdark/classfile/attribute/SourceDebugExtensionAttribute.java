package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * SourceDebugExtension.
 *
 * @author xDark
 */
public final class SourceDebugExtensionAttribute implements Attribute<SourceDebugExtensionAttribute> {

    static final Codec<SourceDebugExtensionAttribute> CODEC = Codec.of(input -> {
        return new SourceDebugExtensionAttribute(input.read(input.readInt()));
    }, (output, value) -> {
        byte[] extension = value.getExtension();
        output.writeInt(extension.length);
        output.write(extension);
    }, Skip.u32());
    private final byte[] extension;

    /**
     * @param extension Extension.
     */
    public SourceDebugExtensionAttribute(byte[] extension) {
        this.extension = extension;
    }

    @Override
    public @NotNull AttributeInfo<SourceDebugExtensionAttribute> info() {
        return AttributeInfo.SourceDebugExtension;
    }

    /**
     * @return Source debug extension.
     */
    public byte[] getExtension() {
        return extension;
    }
}
