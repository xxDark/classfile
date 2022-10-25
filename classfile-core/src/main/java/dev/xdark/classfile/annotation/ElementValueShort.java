package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that points to a short.
 *
 * @author xDark
 */
public final class ElementValueShort extends ElementValueConstant<ElementValueShort> {
    static final Codec<ElementValueShort> CODEC = codec(ElementValueShort::new);

    /**
     * @param index Constant pool entry index.
     */
    public ElementValueShort(int index) {
        super(index);
    }

    @Override
    public @NotNull ElementType<ElementValueShort> type() {
        return ElementType.SHORT;
    }
}
