package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that points to a long.
 *
 * @author xDark
 */
public final class ElementValueLong extends ElementValueConstant<ElementValueLong> {
    static final Codec<ElementValueLong> CODEC = codec(ElementValueLong::new);

    /**
     * @param index Constant pool entry index.
     */
    public ElementValueLong(int index) {
        super(index);
    }

    @Override
    public @NotNull ElementType<ElementValueLong> type() {
        return ElementType.LONG;
    }
}
