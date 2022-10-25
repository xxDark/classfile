package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * Element value that represents an annotation.
 *
 * @author xdark
 */
public final class ElementValueAnnotation implements ElementValue<ElementValueAnnotation> {
    static final Codec<ElementValueAnnotation> CODEC = Codec.of(input -> {
        int typeIndex = input.readUnsignedShort();
        int pairCount = input.readUnsignedShort();
        if (input.remaining() < 3 * pairCount) {
            throw new InvalidAnnotationException("Annotation too small");
        }
        int[] names = new int[pairCount];
        ElementValue<?>[] values = new ElementValue[pairCount];
        for (int i = 0; i < pairCount; i++) {
            names[i] = input.readUnsignedShort();
            int tag = input.readUnsignedByte();
            ElementType<?> type = ElementType.of(tag);
            if (type == null) {
                throw new InvalidAnnotationException("Unknown tag " + (char) tag);
            }
            values[i] = type.codec().read(input);
        }
        return new ElementValueAnnotation(typeIndex, names, values);
    }, (output, value) -> {
        output.writeShort(value.getClassIndex());
        int[] nameIndices = value.getNameIndices();
        ElementValue<?>[] values = value.getValues();
        int count = nameIndices.length;
        if (count != values.length) {
            throw new InvalidAnnotationException("Name and value array have different lengths");
        }
        output.writeShort(count);
        for (int i = 0; i < count; i++) {
            output.writeShort(nameIndices[i]);
            ElementValue<?> v = values[i];
            ElementType<?> type = v.type();
            output.writeByte(type.tag());
            ((Codec) type.codec()).write(output, v);
        }
    });

    private final int classIndex;
    private final int[] nameIndices;
    private final ElementValue<?>[] values;

    /**
     * @param classIndex  Class name index.
     * @param nameIndices Name indices.
     * @param values      Annotation values.
     */
    public ElementValueAnnotation(int classIndex, int[] nameIndices, ElementValue<?>[] values) {
        this.classIndex = classIndex;
        this.nameIndices = nameIndices;
        this.values = values;
    }

    /**
     * @return Class index.
     */
    public int getClassIndex() {
        return classIndex;
    }

    /**
     * @return Name indices.
     */
    public int[] getNameIndices() {
        return nameIndices;
    }

    /**
     * @return Annotation values.
     */
    public ElementValue<?>[] getValues() {
        return values;
    }

    @Override
    public @NotNull ElementType<ElementValueAnnotation> type() {
        return ElementType.ANNOTATION;
    }
}
