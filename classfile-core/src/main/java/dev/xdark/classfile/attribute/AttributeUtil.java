package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Input;
import dev.xdark.classfile.io.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class AttributeUtil {
    private static final int ALLOCATION_LIMIT = 32;

    private AttributeUtil() {
    }

    static int limitAllocationSize(int size) {
        return Math.min(size, ALLOCATION_LIMIT);
    }

    static <T> List<T> readList(Input input, int count, Codec<T> codec) throws IOException {
        List<T> list = new ArrayList<>(limitAllocationSize(count));
        while (count-- != 0) {
            list.add(codec.read(input));
        }
        return list;
    }

    static <T> List<T> readList(Input input, Codec<T> codec) throws IOException {
        return readList(input, input.readUnsignedShort(), codec);
    }

    static <T> void writeList(Output output, List<T> list, Codec<T> codec) throws IOException {
        int size = list.size();
        output.writeShort(size);
        for (int i = 0; i < size; i++) {
            codec.write(output, list.get(i));
        }
    }

    static int[] readUnsignedShorts(Input input, int length) throws IOException {
        int[] arr = new int[length];
        for (int i = 0; i < length; arr[i++] = input.readUnsignedShort()) ;
        return arr;
    }

    static int[] readUnsignedShorts(Input input) throws IOException {
        return readUnsignedShorts(input, input.readUnsignedShort());
    }

    static void writeUnsignedShorts(Output output, int[] values) throws IOException {
        output.writeShort(values.length);
        for (int v : values) {
            output.writeShort(v);
        }
    }
}
