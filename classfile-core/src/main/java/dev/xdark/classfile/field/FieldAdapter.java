package dev.xdark.classfile.field;

import dev.xdark.classfile.AccessFlag;
import dev.xdark.classfile.attribute.AttributeAdapter;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.constantpool.ConstantPoolBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Field adapter.
 */
public class FieldAdapter extends FilterFieldVisitor {
    private final ConstantPoolBuilder builder;

    /**
     * @param fv      Backing fild visitor.
     * @param builder Constant pool builder.
     */
    public FieldAdapter(@NotNull FieldVisitor fv, @NotNull ConstantPoolBuilder builder) {
        super(fv);
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
