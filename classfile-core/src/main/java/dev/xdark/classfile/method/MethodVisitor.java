package dev.xdark.classfile.method;

import dev.xdark.classfile.AccessFlag;
import dev.xdark.classfile.attribute.AttributeVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Method visitor.
 *
 * @author xDark
 */
public interface MethodVisitor {

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
