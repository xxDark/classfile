package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * CONSTANT_InterfaceMethodref.
 *
 * @author xDark
 */
public final class ConstantInterfaceMethodReference extends ConstantReference<ConstantInterfaceMethodReference> {
    static final Codec<ConstantInterfaceMethodReference> CODEC = codec(ConstantInterfaceMethodReference::new);

    /**
     * @param classIndex       Class index.
     * @param nameAndTypeIndex NameAndType index.
     */
    public ConstantInterfaceMethodReference(int classIndex, int nameAndTypeIndex) {
        super(classIndex, nameAndTypeIndex);
    }

    @Override
    public @NotNull Tag<ConstantInterfaceMethodReference> tag() {
        return Tag.CONSTANT_InterfaceMethodref;
    }
}
