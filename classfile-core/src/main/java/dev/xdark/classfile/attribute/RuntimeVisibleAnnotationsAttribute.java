package dev.xdark.classfile.attribute;

import dev.xdark.classfile.annotation.ElementValueAnnotation;
import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * RuntimeVisibleAnnotations.
 *
 * @author xDark
 */
public final class RuntimeVisibleAnnotationsAttribute extends RuntimeAnnotationsAttribute<RuntimeVisibleAnnotationsAttribute> {
    static final Codec<RuntimeVisibleAnnotationsAttribute> CODEC = codec(RuntimeVisibleAnnotationsAttribute::new);

    /**
     * @param annotations List of annotations.
     */
    public RuntimeVisibleAnnotationsAttribute(List<ElementValueAnnotation> annotations) {
        super(annotations);
    }

    @Override
    public @NotNull AttributeInfo<RuntimeVisibleAnnotationsAttribute> info() {
        return AttributeInfo.RuntimeVisibleAnnotations;
    }
}
