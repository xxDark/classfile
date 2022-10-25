package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that points to an int.
 *
 * @author xDark
 */
public final class ElementValueInt extends ElementValueConstant<ElementValueInt> {
    static final Codec<ElementValueInt> CODEC = codec(ElementValueInt::new);

    /**
     * @param index Constant pool entry index.
     */
    public ElementValueInt(int index) {
        super(index);
    }

    @Override
    public @NotNull ElementType<ElementValueInt> type() {
        return ElementType.INT;
    }
}
