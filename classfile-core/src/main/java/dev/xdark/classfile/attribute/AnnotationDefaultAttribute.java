package dev.xdark.classfile.attribute;

import dev.xdark.classfile.annotation.ElementType;
import dev.xdark.classfile.annotation.ElementValue;
import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * AnnotationDefault.
 *
 * @author xDark
 */
public final class AnnotationDefaultAttribute implements Attribute<AnnotationDefaultAttribute> {
    static final Codec<AnnotationDefaultAttribute> CODEC = Codec.of(input -> {
        int length = input.readInt();
        if (length < 3) {
            throw new InvalidAttributeException("Length is less than 3");
        }
        int tag = input.readUnsignedByte();
        ElementType<?> type = ElementType.of(tag);
        if (type == null) {
            throw new InvalidAttributeException("Unknown element type " + (char) tag);
        }
        return new AnnotationDefaultAttribute(type.codec().read(input));
    }, (output, value) -> {
        int position = output.position();
        output.writeInt(0);
        ElementValue<?> v = value.getValue();
        ElementType<?> type = v.type();
        output.writeByte(type.tag());
        ((Codec) type.codec()).write(output, v);
        int newPosition = output.position();
        output.position(position).writeInt(newPosition - position - 4);
        output.position(newPosition);
    });
    private final ElementValue<?> value;

    /**
     * @param value Default value.
     */
    public AnnotationDefaultAttribute(ElementValue<?> value) {
        this.value = value;
    }

    /**
     * @return Default value.
     */
    public ElementValue<?> getValue() {
        return value;
    }

    @Override
    public @NotNull AttributeInfo<AnnotationDefaultAttribute> info() {
        return AttributeInfo.AnnotationDefault;
    }
}
