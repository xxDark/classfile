package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that points to a float.
 *
 * @author xDark
 */
public final class ElementValueFloat extends ElementValueConstant<ElementValueFloat> {
    static final Codec<ElementValueFloat> CODEC = codec(ElementValueFloat::new);

    /**
     * @param index Constant pool entry index.
     */
    public ElementValueFloat(int index) {
        super(index);
    }

    @Override
    public @NotNull ElementType<ElementValueFloat> type() {
        return ElementType.FLOAT;
    }
}
