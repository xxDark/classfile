package dev.xdark.classfile.attribute.type;

import org.jetbrains.annotations.NotNull;

/**
 * target_info union.
 *
 * @author xDark
 */
public interface TargetInfo<SELF extends TargetInfo<SELF>> {

    /**
     * @return Target type.
     */
    @NotNull TargetType<SELF> type();
}
