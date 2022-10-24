package dev.xdark.classfile;

/**
 * JVM access flags.
 *
 * @author xDark
 */
public final class AccessFlag {
    public static final AccessFlag
            ACC_PUBLIC = flag(0x0001),
            ACC_PRIVATE = flag(0x0002),
            ACC_PROTECTED = flag(0x0004),
            ACC_STATIC = flag(0x0008),
            ACC_FINAL = flag(0x0010),
            ACC_SUPER = flag(0x0020),
            ACC_SYNCHRONIZED = flag(0x0020),
            ACC_VOLATILE = flag(0x0040),
            ACC_BRIDGE = flag(0x0040),
            ACC_TRANSIENT = flag(0x0080),
            ACC_VARARGS = flag(0x0080),
            ACC_NATIVE = flag(0x0100),
            ACC_INTERFACE = flag(0x0200),
            ACC_ABSTRACT = flag(0x0400),
            ACC_STRICT = flag(0x0800),
            ACC_SYNTHETIC = flag(0x1000),
            ACC_ANNOTATION = flag(0x2000),
            ACC_ENUM = flag(0x4000),
            ACC_MANDATED = flag(0x8000),
            ACC_MODULE = flag(0x8000);


    private final int mask;

    private AccessFlag(int mask) {
        this.mask = mask;
    }

    /**
     * @return Access mask.
     */
    public int mask() {
        return mask;
    }

    /**
     * @param flag Flag to check.
     * @return {@code true} if {@literal this} flag includes the passed flag.
     */
    public boolean includes(AccessFlag flag) {
        return (mask & flag.mask) != 0;
    }

    /**
     * @param other Second flag.
     * @return New flag.
     */
    public AccessFlag or(AccessFlag other) {
        return new AccessFlag(mask | other.mask);
    }

    /**
     * @param other Second flag.
     * @return New flag.
     */
    public AccessFlag and(AccessFlag other) {
        return new AccessFlag(mask & other.mask);
    }

    /**
     * @param other Second flag.
     * @return New flag.
     */
    public AccessFlag strip(AccessFlag other) {
        return new AccessFlag(mask & ~other.mask);
    }

    static AccessFlag flag(int mask) {
        return new AccessFlag(mask);
    }
}
