package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that points to a boolean.
 *
 * @author xDark
 */
public final class ElementValueBoolean extends ElementValueConstant<ElementValueBoolean> {
    static final Codec<ElementValueBoolean> CODEC = codec(ElementValueBoolean::new);

    /**
     * @param index Constant pool entry index.
     */
    public ElementValueBoolean(int index) {
        super(index);
    }

    @Override
    public @NotNull ElementType<ElementValueBoolean> type() {
        return ElementType.BOOLEAN;
    }
}
