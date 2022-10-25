package dev.xdark.classfile.method;

import dev.xdark.classfile.AccessFlag;
import dev.xdark.classfile.attribute.AttributeAdapter;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.constantpool.ConstantPoolBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Method adapter.
 */
public class MethodAdapter extends FilterMethodVisitor {
    private final ConstantPoolBuilder builder;

    /**
     * @param mv      Backing method visitor.
     * @param builder Constant pool builder.
     */
    public MethodAdapter(@NotNull MethodVisitor mv, @NotNull ConstantPoolBuilder builder) {
        super(mv);
        this.builder = builder;
    }

    /**
     * @param access Access flag.
     * @param name   Name.
     * @param desc   Descriptor.
     */
    public void visit(AccessFlag access, String name, String desc) {
        visit(access, builder.putUtf8(name), builder.putUtf8(desc));
    }

    @Override
    public AttributeAdapter visitAttributes() {
        AttributeVisitor av = super.visitAttributes();
        return av == null ? null : new AttributeAdapter(av, builder);
    }
}
