package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

/**
 * empty_target.
 *
 * @author xDark
 */
public final class EmptyTargetInfo implements TargetInfo<EmptyTargetInfo> {
    private final TargetType<EmptyTargetInfo> type;
    static final Codec<EmptyTargetInfo> CODEC = Codec.of(input -> {
        return new EmptyTargetInfo(TargetType.require(input));
    }, (output, value) -> {
        output.writeByte(value.type().kind());
    });

    /**
     * @param type Target type.
     */
    public EmptyTargetInfo(TargetType<EmptyTargetInfo> type) {
        this.type = type;
    }

    @Override
    public @NotNull TargetType<EmptyTargetInfo> type() {
        return type;
    }
}
