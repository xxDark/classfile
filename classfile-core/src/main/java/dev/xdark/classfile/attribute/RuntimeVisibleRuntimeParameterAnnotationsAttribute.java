package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * RuntimeVisibleParameterAnnotations.
 *
 * @author xDark
 */
public final class RuntimeVisibleRuntimeParameterAnnotationsAttribute extends RuntimeParameterAnnotationsAttribute<RuntimeVisibleRuntimeParameterAnnotationsAttribute> {
    static final Codec<RuntimeVisibleRuntimeParameterAnnotationsAttribute> CODEC = codec(RuntimeVisibleRuntimeParameterAnnotationsAttribute::new);

    /**
     * @param parameterAnnotations List of parameter annotations.
     */
    public RuntimeVisibleRuntimeParameterAnnotationsAttribute(List<ParameterAnnotations> parameterAnnotations) {
        super(parameterAnnotations);
    }

    @Override
    public @NotNull AttributeInfo<RuntimeVisibleRuntimeParameterAnnotationsAttribute> info() {
        return AttributeInfo.RuntimeVisibleParameterAnnotations;
    }
}
