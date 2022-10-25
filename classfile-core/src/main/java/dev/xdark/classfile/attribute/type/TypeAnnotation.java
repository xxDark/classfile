package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.annotation.ElementType;
import dev.xdark.classfile.annotation.ElementValueAnnotation;
import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.io.Codec;

/**
 * Type annotation.
 *
 * @author xDark
 */
public final class TypeAnnotation {
    public static final Codec<TypeAnnotation> CODEC = Codec.of(input -> {
        int position = input.position();
        int kind = input.readUnsignedByte();
        TargetType<?> type = TargetType.of(kind);
        if (type == null) {
            throw new InvalidAttributeException("Unknown target type " + kind);
        }
        input.position(position);
        TargetInfo<?> info = type.codec().read(input);
        TypePath path = TypePath.CODEC.read(input);
        ElementValueAnnotation annotation = ElementType.ANNOTATION.codec().read(input);
        return new TypeAnnotation(info, path, annotation);
    }, (output, value) -> {
        TargetInfo<?> info = value.getInfo();
        TargetType<?> type = info.type();
        output.writeByte(type.kind());
        ((Codec) type.codec()).write(output, info);
        TypePath.CODEC.write(output, value.getTypePath());
        ElementType.ANNOTATION.codec().write(output, value.getAnnotation());
    });
    private final TargetInfo<?> info;
    private final TypePath typePath;
    private final ElementValueAnnotation annotation;

    /**
     * @param info       Target info.
     * @param typePath   Type path.
     * @param annotation Annotation.
     */
    public TypeAnnotation(TargetInfo<?> info, TypePath typePath, ElementValueAnnotation annotation) {
        this.info = info;
        this.typePath = typePath;
        this.annotation = annotation;
    }

    /**
     * @return Target info.
     */
    public TargetInfo<?> getInfo() {
        return info;
    }

    /**
     * @return Type path.
     */
    public TypePath getTypePath() {
        return typePath;
    }

    /**
     * @return Annotation.
     */
    public ElementValueAnnotation getAnnotation() {
        return annotation;
    }
}
