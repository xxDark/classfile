package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that points to a double.
 *
 * @author xDark
 */
public final class ElementValueDouble extends ElementValueConstant<ElementValueDouble> {
    static final Codec<ElementValueDouble> CODEC = codec(ElementValueDouble::new);

    /**
     * @param index Constant pool entry index.
     */
    public ElementValueDouble(int index) {
        super(index);
    }

    @Override
    public @NotNull ElementType<ElementValueDouble> type() {
        return ElementType.DOUBLE;
    }
}
