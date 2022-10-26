package dev.xdark.classfile;

import dev.xdark.classfile.attribute.Attribute;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.attribute.FilterAttributeVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * Filters out unknown attributes.
 *
 * @author xDark
 */
public final class UnknownAttributeFilter extends FilterAttributeVisitor {

    public UnknownAttributeFilter(@NotNull AttributeVisitor av) {
        super(av);
    }

    @Override
    public void visitAttribute(int nameIndex, @NotNull Attribute<?> attribute) {
        if (attribute.info().known() == null) {
            return;
        }
        super.visitAttribute(nameIndex, attribute);
    }
}
