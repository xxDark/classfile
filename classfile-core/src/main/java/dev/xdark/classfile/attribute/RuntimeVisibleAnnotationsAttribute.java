package dev.xdark.classfile.attribute;

import dev.xdark.classfile.annotation.ElementType;
import dev.xdark.classfile.annotation.ElementValueAnnotation;
import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * RuntimeVisibleAnnotations.
 */
public final class RuntimeVisibleAnnotationsAttribute implements Attribute<RuntimeVisibleAnnotationsAttribute> {
    static final Codec<RuntimeVisibleAnnotationsAttribute> CODEC = Codec.of(input -> {
        int length = input.readInt();
        if (length < 2 + 4 /* count + initial information for the annotation (typeIndex, pairCount) */) {
            throw new InvalidAttributeException("Length is less than 6");
        }
        return new RuntimeVisibleAnnotationsAttribute(AttributeUtil.readList(input, ElementType.ANNOTATION.codec()));
    }, (output, value) -> {
        int position = output.position();
        output.writeInt(0);
        AttributeUtil.writeList(output, value.getAnnotations(), ElementType.ANNOTATION.codec());
        int newPosition = output.position();
        output.position(position).writeInt(newPosition - position - 4);
        output.position(newPosition);
    });
    private final List<ElementValueAnnotation> annotations;

    /**
     * @param annotations List of annotations.
     */
    public RuntimeVisibleAnnotationsAttribute(List<ElementValueAnnotation> annotations) {
        this.annotations = annotations;
    }

    /**
     * @return List of annotations.
     */
    public List<ElementValueAnnotation> getAnnotations() {
        return annotations;
    }

    @Override
    public @NotNull AttributeInfo<RuntimeVisibleAnnotationsAttribute> info() {
        return AttributeInfo.RuntimeVisibleAnnotations;
    }
}
