package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.io.Codec;

import java.util.ArrayList;
import java.util.List;

/**
 * type_path.
 *
 * @author xDark
 */
public final class TypePath {
    public static final Codec<TypePath> CODEC = Codec.of(input -> {
        int length = input.readUnsignedByte();
        if (input.remaining() < 2 * length) {
            throw new InvalidAttributeException("Invalid type path");
        }
        List<Path> paths = new ArrayList<>(length);
        while (length-- != 0) {
            paths.add(Path.CODEC.read(input));
        }
        return new TypePath(paths);
    }, (output, value) -> {
        List<Path> paths = value.getPaths();
        int count = paths.size();
        output.writeByte(count);
        for (int i = 0; i < count; i++) {
            Path.CODEC.write(output, paths.get(i));
        }
    });
    private final List<Path> paths;

    /**
     * @param paths List of paths.
     */
    public TypePath(List<Path> paths) {
        this.paths = paths;
    }

    /**
     * @return List of paths.
     */
    public List<Path> getPaths() {
        return paths;
    }

    public static final class Path {
        public static final Codec<Path> CODEC = Codec.of(input -> {
            return new Path(input.readUnsignedByte(), input.readUnsignedByte());
        }, (output, value) -> {
            output.writeByte(value.getKind());
            output.writeByte(value.getIndex());
        });
        private final int kind;
        private final int index;

        /**
         * @param kind  Path kind.
         * @param index Argument index.
         */
        public Path(int kind, int index) {
            this.kind = kind;
            this.index = index;
        }

        /**
         * @return Path kind.
         */
        public int getKind() {
            return kind;
        }

        /**
         * @return Argument index.
         */
        public int getIndex() {
            return index;
        }
    }
}
