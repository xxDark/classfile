package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Utf8.
 *
 * @author xDark
 */
public final class ConstantUtf8 implements ConstantEntry<ConstantUtf8>, ValueEntry<String> {
    static final Codec<ConstantUtf8> CODEC = Codec.of(input -> {
        return new ConstantUtf8(input.readUTF());
    }, (output, value) -> {
        output.writeUTF(value.value());
    });

    private final String value;

    /**
     * @param value UTF-8 value.
     */
    public ConstantUtf8(String value) {
        this.value = value;
    }

    /**
     * @return UTF-8 value.
     */
    public String value() {
        return value;
    }

    @Override
    public @NotNull String getValue() {
        return value;
    }

    @Override
    public @NotNull Tag<ConstantUtf8> tag() {
        return Tag.CONSTANT_Utf8;
    }
}
