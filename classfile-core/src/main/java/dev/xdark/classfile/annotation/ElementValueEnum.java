package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that represents enum constant.
 *
 * @author xDark
 */
public final class ElementValueEnum implements ElementValue<ElementValueEnum> {
    static final Codec<ElementValueEnum> CODEC = Codec.of(input -> {
        return new ElementValueEnum(input.readUnsignedShort(), input.readUnsignedShort());
    }, (output, value) -> {
        output.writeShort(value.getClassIndex());
        output.writeShort(value.getNameIndex());
    }, Skip.exact(4));
    private final int classIndex;
    private final int nameIndex;

    /**
     * @param classIndex Constant pool entry index to a enum class.
     * @param nameIndex  Constant pool entry index to a enum name.
     */
    public ElementValueEnum(int classIndex, int nameIndex) {
        this.classIndex = classIndex;
        this.nameIndex = nameIndex;
    }

    /**
     * @return Constant pool entry index to a enum class.
     */
    public int getClassIndex() {
        return classIndex;
    }

    /**
     * @return Constant pool entry index to a enum name.
     */
    public int getNameIndex() {
        return nameIndex;
    }

    @Override
    public @NotNull ElementType<ElementValueEnum> type() {
        return ElementType.ENUM;
    }
}
