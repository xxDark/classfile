package dev.xdark.classfile;

import org.jetbrains.annotations.Nullable;

/**
 * Class file version.
 *
 * @author xDark
 */
public final class ClassVersion {

    public static final ClassVersion
            V0 = make(45),
            V1 = new ClassVersion(3, 45),
            V2 = make(46),
            V3 = make(47),
            V4 = make(48),
            V5 = make(49),
            V6 = make(50),
            V7 = make(51),
            V8 = make(52),
            V9 = make(53),
            V10 = make(54),
            V11 = make(55),
            V12 = make(56),
            V13 = make(57),
            V14 = make(58),
            V15 = make(59),
            V16 = make(60),
            V17 = make(61),
            V18 = make(62),
            V19 = make(63);
    private static final ClassVersion[] VERSIONS = {
            V0,
            V1,
            V2,
            V3,
            V4,
            V5,
            V6,
            V7,
            V8,
            V9,
            V10,
            V11,
            V12,
            V13,
            V14,
            V15,
            V16,
            V17,
            V18,
            V19,
    };

    private static final int CLASS_VERSION_OFFSET = 44;
    private final int majorVersion;
    private final int minorVersion;

    private ClassVersion(int majorVersion, int minorVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    /**
     * @return Class major version.
     */
    public int majorVersion() {
        return majorVersion;
    }

    /**
     * @return Class minor version.
     */
    public int minorVersion() {
        return minorVersion;
    }

    /**
     * Maps major version to normalised JDK version, e.g.:
     * 46 - 2
     * 47 - 3
     * 48 - 4
     * etc.
     *
     * @return Normalised version.
     * @apiNote This method will return version {@literal 1} for
     * both {@link ClassVersion#V0} and {@link ClassVersion#V1}.
     */
    public int normalisedVersion() {
        return majorVersion - CLASS_VERSION_OFFSET;
    }

    /**
     * @param majorVersion Class major version.
     * @param minorVersion Minor version.
     * @return Version instance or {@code null},
     * if it is invalid.
     */
    @Nullable
    public static ClassVersion of(int majorVersion, int minorVersion) {
        if (majorVersion < ClassVersion.V1.majorVersion() || majorVersion > ClassVersion.V19.majorVersion()) {
            throw new IllegalArgumentException(Integer.toString(majorVersion));
        }
        if (minorVersion == 0) {
            return VERSIONS[majorVersion - CLASS_VERSION_OFFSET];
        }
        return new ClassVersion(majorVersion, minorVersion);
    }

    private static ClassVersion make(int version) {
        return new ClassVersion(version, 0);
    }
}
