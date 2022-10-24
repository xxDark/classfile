package dev.xdark.classfile.field;

import dev.xdark.classfile.AccessFlag;
import dev.xdark.classfile.attribute.AttributeVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Field visitor.
 *
 * @author xDark
 * for example, some class may call {@link FieldVisitor#visitAttributes()} or any other method
 * before calling {@link FieldVisitor#visit(AccessFlag, int, int)}.
 */
public interface FieldVisitor {

    /**
     * @param access          Access flag.
     * @param nameIndex       Name index.
     * @param descriptorIndex Descriptor index.
     */
    void visit(@NotNull AccessFlag access, int nameIndex, int descriptorIndex);

    /**
     * @return New attribute visitor or {@code null},
     * if attributes should not be visited.
     */
    @Nullable AttributeVisitor visitAttributes();

    /**
     * Called after method has been visited.
     */
    void visitEnd();
}
