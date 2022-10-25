package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.dynamic.MethodHandle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.xdark.classfile.dynamic.ReferenceKind.*;

/**
 * Constant pool builder.
 *
 * @author xDark
 */
public final class ConstantPoolBuilder implements ConstantPoolVisitor {
    private final Map<ConstantEntry<?>, Integer> indexMap = new HashMap<>();
    private final List<ConstantEntry<?>> entries = new ArrayList<>();

    public ConstantPoolBuilder() {
        entries.add(null);
    }

    /**
     * @return Constructed constant pool.
     * @apiNote Calling this method multiple times may
     * lead to unexpected behaviour.
     */
    public ConstantPool build() {
        return new BuiltConstantPool(entries);
    }

    /**
     * Adds new entry to the pool.
     *
     * @param entry Entry to add.
     * @return Entry index.
     */
    public int put(@NotNull ConstantEntry<?> entry) {
        Map<ConstantEntry<?>, Integer> indexMap = this.indexMap;
        Integer index = indexMap.get(entry);
        if (index == null) {
            List<ConstantEntry<?>> entries = this.entries;
            index = entries.size();
            indexMap.put(entry, index);
            entries.add(entry);
            if (entry.tag().size() == 2) {
                entries.add(null);
            }
        }
        return index;
    }

    /**
     * Puts all entries from the constant pool.
     *
     * @param constantPool Constant pool to get entries of.
     */
    public void put(@NotNull ConstantPool constantPool) {
        for (ConstantEntry<?> entry : constantPool) {
            put(entry);
        }
    }

    /**
     * Adds CONSTANT_Utf8.
     *
     * @param value String value.
     * @return Entry index.
     */
    public int putUtf8(@NotNull String value) {
        return put(new ConstantUtf8(value));
    }

    /**
     * Adds CONSTANT_Integer.
     *
     * @param value Int value.
     * @return Entry index.
     */
    public int putInt(int value) {
        return put(new ConstantInteger(value));
    }

    /**
     * Adds CONSTANT_Float.
     *
     * @param value Float value.
     * @return Entry index.
     */
    public int putFloat(float value) {
        return put(new ConstantFloat(value));
    }

    /**
     * Adds CONSTANT_Long.
     *
     * @param value Long value.
     * @return Entry index.
     */
    public int putLong(long value) {
        return put(new ConstantLong(value));
    }

    /**
     * Adds CONSTANT_Double.
     *
     * @param value Double value.
     * @return Entry index.
     */
    public int putDouble(double value) {
        return put(new ConstantDouble(value));
    }

    /**
     * Adds CONSTANT_Class.
     *
     * @param value Class name.
     * @return Entry index.
     */
    public int putClass(@NotNull String value) {
        return put(new ConstantClass(putUtf8(value)));
    }

    /**
     * Adds CONSTANT_String.
     *
     * @param value String name.
     * @return Entry index.
     */
    public int putString(@NotNull String value) {
        return put(new ConstantString(putUtf8(value)));
    }

    /**
     * Adds CONSTANT_Fieldref.
     *
     * @param owner Field owner.
     * @param name  Field name.
     * @param desc  Field descriptor.
     * @return Entry index.
     */
    public int putFieldRef(@NotNull String owner, @NotNull String name, @NotNull String desc) {
        return put(new ConstantFieldReference(putClass(owner), putNameAndType(name, desc)));
    }

    /**
     * Adds CONSTANT_Methodref.
     *
     * @param owner Method owner.
     * @param name  Method name.
     * @param desc  Method descriptor.
     * @return Entry index.
     */
    public int  putMethodRef(@NotNull String owner, @NotNull String name, @NotNull String desc) {
        return put(new ConstantMethodReference(putClass(owner), putNameAndType(name, desc)));
    }

    /**
     * Adds CONSTANT_InterfaceMethodref.
     *
     * @param owner Method owner.
     * @param name  Method name.
     * @param desc  Method descriptor.
     * @return Entry index.
     */
    public int putInterfaceMethodRef(@NotNull String owner, @NotNull String name, @NotNull String desc) {
        return put(new ConstantInterfaceMethodReference(putClass(owner), putNameAndType(name, desc)));
    }

    /**
     * Adds CONSTANT_NameAndType.
     *
     * @param name Name.
     * @param type Type.
     * @return Entry index.
     */
    public int putNameAndType(@NotNull String name, @NotNull String type) {
        return put(new ConstantNameAndType(putUtf8(name), putUtf8(type)));
    }

    /**
     * Adds CONSTANT_MethodHandle.
     *
     * @param methodHandle MethodHandle.
     * @return Entry index.
     */
    public int putMethodHandle(@NotNull MethodHandle methodHandle) {
        int referenceKind = methodHandle.getReferenceKind();
        if (referenceKind < REF_GETFIELD || referenceKind > REF_INVOKEINTERFACE) {
            throw new IllegalArgumentException("Illegal reference kind " + referenceKind);
        }
        String owner = methodHandle.getOwner();
        String name = methodHandle.getName();
        String descriptor = methodHandle.getDescriptor();
        int reference;
        switch (referenceKind) {
            case REF_GETFIELD:
            case REF_GETSTATIC:
            case REF_PUTFIELD:
            case REF_PUTSTATIC:
                reference = putFieldRef(owner, name, descriptor);
                break;
            case REF_INVOKEVIRTUAL:
            case REF_NEWINVOKESPECIAL:
                reference = putMethodRef(owner, name, descriptor);
                break;
            default:
                reference = methodHandle.isInterface() ? putInterfaceMethodRef(owner, name, descriptor) : putMethodRef(owner, name, descriptor);
        }
        return put(new ConstantethodHandle(referenceKind, reference));
    }

    /**
     * Adds CONSTANT_MethodType.
     *
     * @param descriptor Method type descriptor.
     * @return Entry index.
     */
    public int putMethodType(String descriptor) {
        return put(new ConstantMethodType(putUtf8(descriptor)));
    }

    /**
     * Adds CONSTANT_InvokeDynamic.
     *
     * @param bootstrapMethodIndex BootstrapMethod index.
     * @param name                 Name.
     * @param desc                 Descriptor.
     * @return Entry index.
     */
    public int putInvokeDynamic(int bootstrapMethodIndex, String name, String desc) {
        return put(new ConstantInvokeDynamic(bootstrapMethodIndex, putNameAndType(name, desc)));
    }

    // TODO putConstantDynamic

    /**
     * Adds CONSTANT_Module.
     *
     * @param module Module name.
     * @return Entry index.
     */
    public int putModule(String module) {
        return put(new ConstantModule(putUtf8(module)));
    }

    /**
     * Adds CONSTANT_Module.
     *
     * @param packageName Package name.
     * @return Entry index.
     */
    public int putPackage(String packageName) {
        return put(new ConstantPackage(putUtf8(packageName)));
    }

    @Override
    public void visitConstants() {
        List<ConstantEntry<?>> entries = this.entries;
        entries.clear();
        entries.add(null);
    }

    @Override
    public void visitConstant(@NotNull ConstantEntry<?> entry) {
        List<ConstantEntry<?>> entries = this.entries;
        entries.add(entry);
        if (entry.tag().size() == 2) {
            entries.add(null);
        }
    }

    @Override
    public void visitEnd() {
    }
}
