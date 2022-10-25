package dev.xdark.classfile;

import dev.xdark.classfile.constantpool.ConstantPoolBuilder;
import dev.xdark.classfile.field.FieldAdapter;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.method.MethodAdapter;
import dev.xdark.classfile.method.MethodVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * Class adapter.
 *
 * @author xDark
 */
public class ClassAdapter extends FilterClassVisitor {
    protected final ConstantPoolBuilder builder;

    /**
     * @param cv      Backing class visitor.
     * @param builder Constant pool builder.
     */
    public ClassAdapter(@NotNull ClassVisitor cv, ConstantPoolBuilder builder) {
        super(cv);
        this.builder = builder;
    }

    /**
     * @param version    Class version.
     * @param access     Access flags.
     * @param name       Class name.
     * @param superName  Super name.
     * @param interfaces Interface names.
     */
    public void visit(@NotNull ClassVersion version, @NotNull AccessFlag access, @NotNull String name, @Nullable String superName, @NotNull List<String> interfaces) {
        ConstantPoolBuilder builder = this.builder;
        visit(version, access, builder.putClass(name), superName == null ? 0 : builder.putClass(superName), interfaces.stream().mapToInt(builder::putClass).toArray());
    }

    /**
     * @param version   Class version.
     * @param access    Access flags.
     * @param name      Class name.
     * @param superName Super name.
     */
    public void visit(@NotNull ClassVersion version, @NotNull AccessFlag access, @NotNull String name, @Nullable String superName) {
        visit(version, access, name, superName, Collections.emptyList());
    }

    @Nullable
    public MethodAdapter visitMethod(AccessFlag access, String name, String desc) {
        return visitMethod(access, builder.putUtf8(name), builder.putUtf8(desc));
    }

    @Nullable
    public FieldAdapter visitField(AccessFlag access, String name, String desc) {
        return visitField(access, builder.putUtf8(name), builder.putUtf8(desc));
    }

    @Override
    public MethodAdapter visitMethod(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        MethodVisitor mv = super.visitMethod(access, nameIndex, descriptorIndex);
        return mv == null ? null : new MethodAdapter(mv, builder);
    }

    @Override
    public FieldAdapter visitField(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
        FieldVisitor fv = super.visitField(access, nameIndex, descriptorIndex);
        return fv == null ? null : new FieldAdapter(fv, builder);
    }
}
