package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Element value that represents an array.
 *
 * @author xdark
 */
public final class ElementValueArray implements ElementValue<ElementValueArray> {
    static final Codec<ElementValueArray> CODEC = Codec.of(input -> {
        int count = input.readUnsignedShort();
        if (input.remaining() < count * 3) {
            throw new InvalidAnnotationException("Annotation too small");
        }
        ElementValue<?>[] values = new ElementValue[count];
        for (int i = 0; i < count; i++) {
            int tag = input.readUnsignedByte();
            ElementType<?> type = ElementType.of(tag);
            if (type == null) {
                throw new InvalidAnnotationException("Unknown tag " + (char) tag);
            }
            values[i] = type.codec().read(input);
        }
        return new ElementValueArray(values);
    }, (output, value) -> {
        ElementValue<?>[] values = value.getValues();
        output.writeShort(values.length);
        for (ElementValue<?> v : values) {
            ((Codec) v.type().codec()).write(output, v);
        }
    });
    private final ElementValue<?>[] values;

    /**
     * @param values Array of values.
     */
    public ElementValueArray(ElementValue<?>[] values) {
        this.values = values;
    }

    /**
     * @return Array of values.
     */
    public ElementValue<?>[] getValues() {
        return values;
    }

    @Override
    public @NotNull ElementType<ElementValueArray> type() {
        return null;
    }
}
