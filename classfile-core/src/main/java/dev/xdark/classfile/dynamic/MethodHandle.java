package dev.xdark.classfile.dynamic;

import org.jetbrains.annotations.NotNull;

/**
 * Method handle.
 *
 * @author xDark
 */
public final class MethodHandle {
    private final int referenceKind;
    private final String owner;
    private final String name;
    private final String descriptor;
    private final boolean itf;

    /**
     * @param referenceKind Reference kind.
     * @param owner         Owner class name.
     * @param name          Name.
     * @param descriptor    Descriptor.
     * @param itf           Whether the owner is an interface.
     */
    public MethodHandle(int referenceKind, @NotNull String owner, @NotNull String name, @NotNull String descriptor, boolean itf) {
        this.referenceKind = referenceKind;
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
        this.itf = itf;
    }

    /**
     * @return Reference kind.
     */
    public int getReferenceKind() {
        return referenceKind;
    }

    /**
     * @return Owner class name.
     */
    @NotNull
    public String getOwner() {
        return owner;
    }

    /**
     * @return Name.
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * @return Descriptor.
     */
    @NotNull
    public String getDescriptor() {
        return descriptor;
    }

    /**
     * @return Whether the owner is an interface.
     */
    public boolean isInterface() {
        return itf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodHandle that = (MethodHandle) o;

        if (referenceKind != that.referenceKind) return false;
        if (itf != that.itf) return false;
        if (!owner.equals(that.owner)) return false;
        if (!name.equals(that.name)) return false;
        return descriptor.equals(that.descriptor);
    }

    @Override
    public int hashCode() {
        int result = referenceKind;
        result = 31 * result + owner.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + descriptor.hashCode();
        result = 31 * result + (itf ? 1 : 0);
        return result;
    }
}
