package dev.xdark.classfile.constantpool;

import org.jetbrains.annotations.NotNull;

/**
 * Constant entry that holds known value,
 * such as long, double, int float, string, etc.
 *
 * @author xDark
 */
public interface ValueEntry<V> {

    /**
     * @return Known value.
     */
    @NotNull V getValue();
}
