package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that points to a string.
 *
 * @author xDark
 */
public final class ElementValueString extends ElementValueConstant<ElementValueString> {
    static final Codec<ElementValueString> CODEC = codec(ElementValueString::new);

    /**
     * @param index Constant pool entry index.
     */
    public ElementValueString(int index) {
        super(index);
    }

    @Override
    public @NotNull ElementType<ElementValueString> type() {
        return ElementType.STRING;
    }
}
