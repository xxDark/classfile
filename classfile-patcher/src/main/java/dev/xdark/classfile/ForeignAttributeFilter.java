package dev.xdark.classfile;

import dev.xdark.classfile.attribute.*;
import org.jetbrains.annotations.NotNull;

/**
 * Filters out foreign attributes.
 *
 * @author xDark
 * @apiNote Note that this filter will not remove unknown attributes.
 * @see UnknownAttributeFilter
 */
public final class ForeignAttributeFilter extends FilterAttributeVisitor {
    private final AttributeLocation location;

    /**
     * @param av       Backing attribute visitor.
     * @param location To which location this visitor belongs.
     */
    public ForeignAttributeFilter(@NotNull AttributeVisitor av, @NotNull AttributeLocation location) {
        super(av);
        this.location = location;
    }

    @Override
    public void visitAttribute(int nameIndex, @NotNull Attribute<?> attribute) {
        KnownInfo known = attribute.info().known();
        if (known != null && !known.locations().contains(location)) {
            return;
        }
        super.visitAttribute(nameIndex, attribute);
    }
}
