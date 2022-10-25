package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.attribute.Attribute;
import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.io.Codec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Shared code between Runtime[In]visibleTypeAnnotations.
 *
 * @author xDark
 */
public abstract class RuntimeTypeAnnotationsAttribute<SELF extends RuntimeTypeAnnotationsAttribute<SELF>> implements Attribute<SELF> {
    private final List<TypeAnnotation> annotations;

    /**
     * @param annotations List of annotations.
     */
    protected RuntimeTypeAnnotationsAttribute(List<TypeAnnotation> annotations) {
        this.annotations = annotations;
    }

    /**
     * @return List of annotations.
     */
    public final List<TypeAnnotation> getAnnotations() {
        return annotations;
    }

    protected static <T extends RuntimeTypeAnnotationsAttribute<T>> Codec<T> codec(Function<List<TypeAnnotation>, T> fn) {
        return Codec.of(input -> {
            int length = input.readInt();
            if (length < 2) {
                throw new InvalidAttributeException("Length is less than 2");
            }
            int count = input.readUnsignedShort();
            List<TypeAnnotation> annotations = new ArrayList<>(Math.min(32, count));
            while (count-- != 0) {
                annotations.add(TypeAnnotation.CODEC.read(input));
            }
            return fn.apply(annotations);
        }, (output, value) -> {
            int position = output.position();
            output.writeInt(0);
            List<TypeAnnotation> annotations = value.getAnnotations();
            int count = annotations.size();
            output.writeShort(count);
            for (int i = 0; i < count; i++) {
                TypeAnnotation.CODEC.write(output, annotations.get(i));
            }
            int newPosition = output.position();
            output.position(position).writeInt(newPosition - position - 4);
            output.position(newPosition);
        });
    }
}
