package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.attribute.AttributeInfo;
import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * RuntimeInvisibleTypeAnnotations.
 *
 * @author xDark
 */
public final class RuntimeInvisibleTypeAnnotationsAttribute extends RuntimeTypeAnnotationsAttribute<RuntimeInvisibleTypeAnnotationsAttribute> {
    public static final Codec<RuntimeInvisibleTypeAnnotationsAttribute> CODEC = codec(RuntimeInvisibleTypeAnnotationsAttribute::new);

    /**
     * @param annotations List of annotations.
     */
    public RuntimeInvisibleTypeAnnotationsAttribute(List<TypeAnnotation> annotations) {
        super(annotations);
    }

    @Override
    public @NotNull AttributeInfo<RuntimeInvisibleTypeAnnotationsAttribute> info() {
        return AttributeInfo.RuntimeInvisibleTypeAnnotations;
    }
}
