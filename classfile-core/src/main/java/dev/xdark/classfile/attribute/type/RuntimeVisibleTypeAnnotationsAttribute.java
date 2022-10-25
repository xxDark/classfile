package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.attribute.AttributeInfo;
import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * RuntimeVisibleTypeAnnotations.
 *
 * @author xDark
 */
public final class RuntimeVisibleTypeAnnotationsAttribute extends RuntimeTypeAnnotationsAttribute<RuntimeVisibleTypeAnnotationsAttribute> {
    public static final Codec<RuntimeVisibleTypeAnnotationsAttribute> CODEC = codec(RuntimeVisibleTypeAnnotationsAttribute::new);

    /**
     * @param annotations List of annotations.
     */
    public RuntimeVisibleTypeAnnotationsAttribute(List<TypeAnnotation> annotations) {
        super(annotations);
    }

    @Override
    public @NotNull AttributeInfo<RuntimeVisibleTypeAnnotationsAttribute> info() {
        return AttributeInfo.RuntimeVisibleTypeAnnotations;
    }
}
