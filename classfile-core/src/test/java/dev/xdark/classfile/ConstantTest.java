package dev.xdark.classfile;

import dev.xdark.classfile.constantpool.*;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.buffer.ByteBufferAllocator;
import dev.xdark.classfile.io.buffer.ByteBufferInput;
import dev.xdark.classfile.io.buffer.ByteBufferOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class ConstantTest {

    @MethodSource("entries")
    @ParameterizedTest
    public void doTest(ConstantEntry<?> entry) throws IOException {
        ByteBufferOutput output = new ByteBufferOutput(ByteBufferAllocator.HEAP);
        Codec<ConstantEntry<?>> codec = (Codec<ConstantEntry<?>>) entry.tag().codec();
        codec.write(output, entry);
        ByteBuffer buffer = output.consume();
        ConstantEntry<?> copy = codec.read(new ByteBufferInput(buffer.slice()));
        ByteBufferOutput output2 = new ByteBufferOutput(ByteBufferAllocator.HEAP);
        codec.write(output2, copy);
        Assertions.assertEquals(buffer, output2.consume());
    }

    private static List<ConstantEntry<?>> entries() {
        return Arrays.asList(
                new ConstantUtf8("hello, world"),
                new ConstantInteger(Long.hashCode(System.currentTimeMillis())),
                new ConstantFloat((float) Math.random()),
                new ConstantLong(System.nanoTime()),
                new ConstantDouble(Math.random()),
                new ConstantClass(5),
                new ConstantString(10),
                new ConstantFieldReference(1, 2),
                new ConstantMethodReference(3, 4),
                new ConstantInterfaceMethodReference(100, 200),
                new ConstantNameAndType(500, 600),
                new ConstantethodHandle(1, 4)
        );
    }
}
