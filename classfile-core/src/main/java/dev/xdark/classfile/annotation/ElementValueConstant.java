package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

import java.util.function.IntFunction;

/**
 * Annotation element value that points to an
 * entry in the constant pool.
 *
 * @author xDark
 */
public abstract class ElementValueConstant<T extends ElementValueConstant<T>> implements ElementValue<T> {
    private final int index;

    /**
     * @param index Constant pool entry index.
     */
    protected ElementValueConstant(int index) {
        this.index = index;
    }

    /**
     * @return Constant pool entry index.
     */
    public int getIndex() {
        return index;
    }

    protected static <T extends ElementValueConstant<T>> Codec<T> codec(IntFunction<T> fn) {
        return Codec.of(input -> {
            return fn.apply(input.readUnsignedShort());
        }, (output, value) -> {
            output.writeShort(value.getIndex());
        }, Skip.exact(2));
    }
}
