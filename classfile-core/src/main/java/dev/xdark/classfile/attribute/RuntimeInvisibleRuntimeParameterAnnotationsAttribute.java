package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * RuntimeInvisibleParameterAnnotations.
 *
 * @author xDark
 */
public final class RuntimeInvisibleRuntimeParameterAnnotationsAttribute extends RuntimeParameterAnnotationsAttribute<RuntimeInvisibleRuntimeParameterAnnotationsAttribute> {
    static final Codec<RuntimeInvisibleRuntimeParameterAnnotationsAttribute> CODEC = codec(RuntimeInvisibleRuntimeParameterAnnotationsAttribute::new);

    /**
     * @param parameterAnnotations List of parameter annotations.
     */
    public RuntimeInvisibleRuntimeParameterAnnotationsAttribute(List<ParameterAnnotations> parameterAnnotations) {
        super(parameterAnnotations);
    }

    @Override
    public @NotNull AttributeInfo<RuntimeInvisibleRuntimeParameterAnnotationsAttribute> info() {
        return AttributeInfo.RuntimeInvisibleParameterAnnotations;
    }
}
