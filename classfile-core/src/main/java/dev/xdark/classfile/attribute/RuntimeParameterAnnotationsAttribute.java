package dev.xdark.classfile.attribute;

import dev.xdark.classfile.annotation.ElementType;
import dev.xdark.classfile.annotation.ElementValueAnnotation;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;

import java.util.List;
import java.util.function.Function;

/**
 * Shared code between Runtime[In]visibleParameterAnnotations.
 *
 * @author xDark
 */
public abstract class RuntimeParameterAnnotationsAttribute<SELF extends RuntimeParameterAnnotationsAttribute<SELF>> implements Attribute<SELF> {
    private final List<ParameterAnnotations> parameterAnnotations;

    /**
     * @param parameterAnnotations List of parameter annotations.
     */
    protected RuntimeParameterAnnotationsAttribute(List<ParameterAnnotations> parameterAnnotations) {
        this.parameterAnnotations = parameterAnnotations;
    }

    /**
     * @return List of parameter annotations.
     */
    public List<ParameterAnnotations> getParameterAnnotations() {
        return parameterAnnotations;
    }

    protected static <T extends RuntimeParameterAnnotationsAttribute<T>> Codec<T> codec(Function<List<ParameterAnnotations>, T> fn) {
        return Codec.of(input -> {
            int length = input.readInt();
            if (length < 2) {
                throw new InvalidAttributeException("Length is less than 2");
            }
            int count = input.readUnsignedShort();
            if (length < count * 4) {
                throw new InvalidAttributeException("Invalid attribute content");
            }
            return fn.apply(AttributeUtil.readList(input, count, ParameterAnnotations.CODEC));
        }, (output, value) -> {
            int position = output.position();
            output.writeInt(0);
            AttributeUtil.writeList(output, value.getParameterAnnotations(), ParameterAnnotations.CODEC);
            int newPosition = output.position();
            output.position(position).writeInt(newPosition - position - 4);
            output.position(newPosition);
        }, Skip.u32());
    }

    public static final class ParameterAnnotations {
        static final Codec<ParameterAnnotations> CODEC = Codec.of(input -> {
            return new ParameterAnnotations(AttributeUtil.readList(input, ElementType.ANNOTATION.codec()));
        }, (output, value) -> {
            AttributeUtil.writeList(output, value.getAnnotations(), ElementType.ANNOTATION.codec());
        }, input -> {
            for (int i = 0, j = input.readUnsignedShort(); i < j; i++) {
                ElementType.ANNOTATION.codec().skip(input);
            }
        });
        private final List<ElementValueAnnotation> annotations;

        /**
         * @param annotations List of annotations.
         */
        public ParameterAnnotations(List<ElementValueAnnotation> annotations) {
            this.annotations = annotations;
        }

        /**
         * @return List of annotations.
         */
        public List<ElementValueAnnotation> getAnnotations() {
            return annotations;
        }
    }
}
