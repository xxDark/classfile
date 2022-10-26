package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_Methodref.
 *
 * @author xDark
 */
public final class ConstantMethodReference extends ConstantReference<ConstantMethodReference> {
    static final Codec<ConstantMethodReference> CODEC = codec(ConstantMethodReference::new);

    /**
     * @param classIndex       Class index.
     * @param nameAndTypeIndex NameAndType index.
     */
    public ConstantMethodReference(int classIndex, int nameAndTypeIndex) {
        super(classIndex, nameAndTypeIndex);
    }

    @Override
    public @NotNull Tag<ConstantMethodReference> tag() {
        return Tag.CONSTANT_Methodref;
    }
}
