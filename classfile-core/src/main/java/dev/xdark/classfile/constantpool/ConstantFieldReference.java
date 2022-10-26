package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Fieldref.
 *
 * @author xDark
 */
public final class ConstantFieldReference extends ConstantReference<ConstantFieldReference> {
    static final Codec<ConstantFieldReference> CODEC = codec(ConstantFieldReference::new);

    /**
     * @param classIndex       Class index.
     * @param nameAndTypeIndex NameAndType index.
     */
    public ConstantFieldReference(int classIndex, int nameAndTypeIndex) {
        super(classIndex, nameAndTypeIndex);
    }

    @Override
    public @NotNull Tag<ConstantFieldReference> tag() {
        return Tag.CONSTANT_Fieldref;
    }
}
