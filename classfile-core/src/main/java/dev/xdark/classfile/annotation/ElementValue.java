package dev.xdark.classfile.annotation;

import org.jetbrains.annotations.NotNull;

/**
 * Annotation element value.
 *
 * @author xDark
 */
public interface ElementValue<T extends ElementValue<T>> {

    /**
     * @return Element type.
     */
    @NotNull ElementType<T> type();
}
