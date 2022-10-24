package dev.xdark.classfile.attribute;

import java.util.EnumSet;
import java.util.Set;

/**
 * Known attribute info.
 *
 * @author xDark
 */
public final class KnownInfo {
    private final String name;
    private final Set<AttributeLocation> locations;

    private KnownInfo(String name, Set<AttributeLocation> locations) {
        this.name = name;
        this.locations = locations;
    }

    /**
     * @return Attribute name, according to JVM spec.
     */
    public String name() {
        return name;
    }

    /**
     * @return Set of locations where this attribute can appear.
     */
    public Set<AttributeLocation> locations() {
        return locations;
    }

    static KnownInfo kinfo(String name, AttributeLocation... locations) {
        return new KnownInfo(name, EnumSet.of(locations[0], locations));
    }

    static KnownInfo kinfo(String name, AttributeLocation location) {
        return new KnownInfo(name, EnumSet.of(location));
    }

    static KnownInfo kinfo(String name, AttributeLocation location1, AttributeLocation location2) {
        return new KnownInfo(name, EnumSet.of(location1, location2));
    }
}
