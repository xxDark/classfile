package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that points to a char.
 *
 * @author xDark
 */
public final class ElementValueChar extends ElementValueConstant<ElementValueChar> {
    static final Codec<ElementValueChar> CODEC = codec(ElementValueChar::new);

    /**
     * @param index Constant pool entry index.
     */
    public ElementValueChar(int index) {
        super(index);
    }

    @Override
    public @NotNull ElementType<ElementValueChar> type() {
        return ElementType.CHAR;
    }
}
