package dev.xdark.classfile.attribute;

import dev.xdark.classfile.annotation.ElementType;
import dev.xdark.classfile.annotation.ElementValueAnnotation;
import dev.xdark.classfile.io.Codec;

import java.util.List;
import java.util.function.Function;

/**
 * Shared code between Runtime[In]visibleAnnotationsAttribute.
 *
 * @author xDark
 */
public abstract class RuntimeAnnotationsAttribute<SELF extends RuntimeAnnotationsAttribute<SELF>> implements Attribute<SELF> {
    private final List<ElementValueAnnotation> annotations;

    /**
     * @param annotations List of annotations.
     */
    protected RuntimeAnnotationsAttribute(List<ElementValueAnnotation> annotations) {
        this.annotations = annotations;
    }

    /**
     * @return List of annotations.
     */
    public final List<ElementValueAnnotation> getAnnotations() {
        return annotations;
    }

    protected static <T extends RuntimeAnnotationsAttribute<T>> Codec<T> codec(Function<List<ElementValueAnnotation>, T> fn) {
        return Codec.of(input -> {
            int length = input.readInt();
            if (length < 2 + 4 /* count + initial information for the annotation (typeIndex, pairCount) */) {
                throw new InvalidAttributeException("Length is less than 6");
            }
            return fn.apply(AttributeUtil.readList(input, ElementType.ANNOTATION.codec()));
        }, (output, value) -> {
            int position = output.position();
            output.writeInt(0);
            AttributeUtil.writeList(output, value.getAnnotations(), ElementType.ANNOTATION.codec());
            int newPosition = output.position();
            output.position(position).writeInt(newPosition - position - 4);
            output.position(newPosition);
        });
    }
}
