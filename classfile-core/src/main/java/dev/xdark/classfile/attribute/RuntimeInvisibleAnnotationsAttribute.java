package dev.xdark.classfile.attribute;

import dev.xdark.classfile.annotation.ElementValueAnnotation;
import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * RuntimeInvisibleAnnotations.
 *
 * @author xDark
 */
public final class RuntimeInvisibleAnnotationsAttribute extends RuntimeAnnotationsAttribute<RuntimeInvisibleAnnotationsAttribute> {
    static final Codec<RuntimeInvisibleAnnotationsAttribute> CODEC = codec(RuntimeInvisibleAnnotationsAttribute::new);

    /**
     * @param annotations List of annotations.
     */
    public RuntimeInvisibleAnnotationsAttribute(List<ElementValueAnnotation> annotations) {
        super(annotations);
    }

    @Override
    public @NotNull AttributeInfo<RuntimeInvisibleAnnotationsAttribute> info() {
        return AttributeInfo.RuntimeInvisibleAnnotations;
    }
}
