package dev.xdark.classfile;

import dev.xdark.classfile.attribute.Attribute;
import org.jetbrains.annotations.NotNull;

/**
 * Unknown attribute.
 *
 * @author xDark
 */
public final class UnknownStoredAttribute {
    private final String name;
    private final Attribute<?> attribute;

    /**
     * @param name      Attribute name.
     * @param attribute Attribute.
     */
    public UnknownStoredAttribute(@NotNull String name, @NotNull Attribute<?> attribute) {
        this.name = name;
        this.attribute = attribute;
    }

    /**
     * @return Attribute name.
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * @return Attribute.
     */
    @NotNull
    public Attribute<?> getAttribute() {
        return attribute;
    }
}
