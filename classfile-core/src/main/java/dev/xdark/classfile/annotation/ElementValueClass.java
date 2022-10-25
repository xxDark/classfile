package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value that points to a class.
 *
 * @author xDark
 */
public final class ElementValueClass extends ElementValueConstant<ElementValueClass> {
    static final Codec<ElementValueClass> CODEC = codec(ElementValueClass::new);

    /**
     * @param index Constant pool entry index.
     */
    public ElementValueClass(int index) {
        super(index);
    }

    @Override
    public @NotNull ElementType<ElementValueClass> type() {
        return ElementType.CLASS;
    }
}
