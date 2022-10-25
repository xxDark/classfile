package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that points to a byte.
 *
 * @author xDark
 */
public final class ElementValueByte extends ElementValueConstant<ElementValueByte> {
    static final Codec<ElementValueByte> CODEC = codec(ElementValueByte::new);

    /**
     * @param index Constant pool entry index.
     */
    public ElementValueByte(int index) {
        super(index);
    }

    @Override
    public @NotNull ElementType<ElementValueByte> type() {
        return ElementType.BYTE;
    }
}
