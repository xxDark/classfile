package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Module.
 *
 * @author xDark
 */
public final class ModuleAttribute implements Attribute<ModuleAttribute> {
    static final Codec<ModuleAttribute> CODEC = Codec.of(input -> {
        if (input.readInt() < 6) {
            throw new InvalidAttributeException("Module length is less than 6");
        }
        int nameIndex = input.readUnsignedShort();
        int flags = input.readUnsignedShort();
        int version = input.readUnsignedShort();
        List<Require> requires = AttributeUtil.readList(input, Require.CODEC);
        List<Export> exports = AttributeUtil.readList(input, Export.CODEC);
        List<Open> opens = AttributeUtil.readList(input, Open.CODEC);
        int[] uses = AttributeUtil.readUnsignedShorts(input);
        List<Provide> provides = AttributeUtil.readList(input, Provide.CODEC);
        return new ModuleAttribute(nameIndex, flags, version, requires, exports, opens, uses, provides);
    }, (output, value) -> {
        // It is wasteful to count the size first, use patch strategy
        int position = output.position();
        output.writeInt(0);
        output.writeShort(value.getNameIndex());
        output.writeShort(value.getFlags());
        output.writeShort(value.getVersionIndex());
        AttributeUtil.writeList(output, value.getRequires(), Require.CODEC);
        AttributeUtil.writeList(output, value.getExports(), Export.CODEC);
        AttributeUtil.writeList(output, value.getOpens(), Open.CODEC);
        AttributeUtil.writeUnsignedShorts(output, value.getUses());
        AttributeUtil.writeList(output, value.getProvides(), Provide.CODEC);
        int newPosition = output.position();
        output.position(position).writeInt(newPosition - position - 4);
        output.position(newPosition);
    });
    private final int nameIndex;
    private final int flags;
    private final int versionIndex;
    private final List<Require> requires;
    private final List<Export> exports;
    private final List<Open> opens;
    private final int[] uses;
    private final List<Provide> provides;

    /**
     * @param nameIndex    Name index.
     * @param flags        Module flags.
     * @param versionIndex Version index.
     * @param requires     Required modules.
     * @param exports      Exported packages.
     * @param opens        Opened packages.
     * @param uses         Used services.
     * @param provides     Provided services.
     */
    public ModuleAttribute(int nameIndex, int flags, int versionIndex, List<Require> requires, List<Export> exports, List<Open> opens, int[] uses, List<Provide> provides) {
        this.nameIndex = nameIndex;
        this.flags = flags;
        this.versionIndex = versionIndex;
        this.requires = requires;
        this.exports = exports;
        this.opens = opens;
        this.uses = uses;
        this.provides = provides;
    }

    /**
     * @return Name index.
     */
    public int getNameIndex() {
        return nameIndex;
    }

    /**
     * @return Module flags.
     */
    public int getFlags() {
        return flags;
    }

    /**
     * @return Version index.
     */
    public int getVersionIndex() {
        return versionIndex;
    }

    /**
     * @return Required modules.
     */
    public List<Require> getRequires() {
        return requires;
    }

    /**
     * @return Exported packages.
     */
    public List<Export> getExports() {
        return exports;
    }

    /**
     * @return Opened packages.
     */
    public List<Open> getOpens() {
        return opens;
    }

    /**
     * @return Used services.
     */
    public int[] getUses() {
        return uses;
    }

    /**
     * @return Provided services.
     */
    public List<Provide> getProvides() {
        return provides;
    }

    @Override
    public @NotNull AttributeInfo<ModuleAttribute> info() {
        return AttributeInfo.Module;
    }

    public static final class Require {
        public static final Codec<Require> CODEC = Codec.of(input -> {
            return new Require(input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort());
        }, (output, value) -> {
            output.writeShort(value.getIndex());
            output.writeShort(value.getFlags());
            output.writeShort(value.getVersionIndex());
        });
        private final int index;
        private final int flags;
        private final int versionIndex;

        /**
         * @param index        Module index.
         * @param flags        Flags.
         * @param versionIndex Module version index.
         */
        public Require(int index, int flags, int versionIndex) {
            this.index = index;
            this.flags = flags;
            this.versionIndex = versionIndex;
        }

        /**
         * @return Module index.
         */
        public int getIndex() {
            return index;
        }

        /**
         * @return Module flags.
         */
        public int getFlags() {
            return flags;
        }

        /**
         * @return Module version index.
         */
        public int getVersionIndex() {
            return versionIndex;
        }
    }

    public static final class Export {
        public static final Codec<Export> CODEC = Codec.of(input -> {
            int index = input.readUnsignedShort();
            int flags = input.readUnsignedShort();
            int[] indices = AttributeUtil.readUnsignedShorts(input);
            return new Export(index, flags, indices);
        }, (output, value) -> {
            output.writeShort(value.getIndex());
            output.writeShort(value.getFlags());
            AttributeUtil.writeUnsignedShorts(output, value.getExportIndices());
        });
        private final int index;
        private final int flags;
        private final int[] exportIndices;

        /**
         * @param index         Package index.
         * @param flags         Export flags
         * @param exportIndices Module indices.
         */
        public Export(int index, int flags, int[] exportIndices) {
            this.index = index;
            this.flags = flags;
            this.exportIndices = exportIndices;
        }

        /**
         * @return Package index.
         */
        public int getIndex() {
            return index;
        }

        /**
         * @return Export flags.
         */
        public int getFlags() {
            return flags;
        }

        /**
         * @return Module indices.
         */
        public int[] getExportIndices() {
            return exportIndices;
        }
    }

    public static final class Open {
        public static final Codec<Open> CODEC = Codec.of(input -> {
            int index = input.readUnsignedShort();
            int flags = input.readUnsignedShort();
            int[] indices = AttributeUtil.readUnsignedShorts(input);
            return new Open(index, flags, indices);
        }, (output, value) -> {
            output.writeShort(value.getIndex());
            output.writeShort(value.getFlags());
            AttributeUtil.writeUnsignedShorts(output, value.getOpensToIndices());
        });
        private final int index;
        private final int flags;
        private final int[] opensToIndices;

        /**
         * @param index          Package index.
         * @param flags          Open flags.
         * @param opensToIndices Module indices.
         */
        public Open(int index, int flags, int[] opensToIndices) {
            this.index = index;
            this.flags = flags;
            this.opensToIndices = opensToIndices;
        }

        /**
         * @return Package index.
         */
        public int getIndex() {
            return index;
        }

        /**
         * @return Open flags.
         */
        public int getFlags() {
            return flags;
        }

        /**
         * @return Module indices.
         */
        public int[] getOpensToIndices() {
            return opensToIndices;
        }
    }

    public static final class Provide {
        public static final Codec<Provide> CODEC = Codec.of(input -> {
            int index = input.readUnsignedShort();
            int[] services = AttributeUtil.readUnsignedShorts(input);
            return new Provide(index, services);
        }, (output, value) -> {
            output.writeShort(value.getIndex());
            AttributeUtil.writeUnsignedShorts(output, value.getServices());
        });
        private final int index;
        private final int[] services;

        /**
         * @param index    Service interface index.
         * @param services Service implementation indices.
         */
        public Provide(int index, int[] services) {
            this.index = index;
            this.services = services;
        }

        /**
         * @return Service interface index.
         */
        public int getIndex() {
            return index;
        }

        /**
         * @return Service implementation indices.
         */
        public int[] getServices() {
            return services;
        }
    }
}
