package dev.xdark.classfile;

import dev.xdark.classfile.io.buffer.ByteBufferInput;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ClassNodeTest {

    public static final String STRING = "hello, world";
    public static final long LONG = 6767L;
    public static final double DOUBLE = 0.00005D;
    public static final int INT = -321;
    public static final float FLOAT = 100.0F;

    @Test
    public void doTest() throws IOException {
        ClassLoader loader = ClassNodeTest.class.getClassLoader();
        ClassNode classNode = new ClassNode();
        String className = ClassNodeTest.class.getName();
        try (InputStream in = loader.getResourceAsStream(className.replace('.', '/') + ".class")) {
            Objects.requireNonNull(in);
            byte[] buf = new byte[1024];
            ByteBuffer buffer = ByteBuffer.allocate(in.available());
            int r;
            while ((r = in.read(buf)) != -1) {
                buffer.put(buf, 0, r);
            }
            buffer.flip();
            ClassIO.read(new ByteBufferInput(buffer), classNode);
        }
        assertEquals(className, classNode.name);
        assertEquals(ClassNodeTest.class.getSuperclass().getName(), classNode.superName);
        assertEquals(STRING, search(classNode.fields, "STRING").constantValue);
        assertEquals(LONG, search(classNode.fields, "LONG").constantValue);
        assertEquals(DOUBLE, search(classNode.fields, "DOUBLE").constantValue);
        assertEquals(INT, search(classNode.fields, "INT").constantValue);
        assertEquals(FLOAT, search(classNode.fields, "FLOAT").constantValue);
    }

    private static FieldNode search(List<FieldNode> fieldNodeList, String name) {
        Optional<FieldNode> opt = fieldNodeList.stream().filter(x -> name.equals(x.name)).findFirst();
        if (!opt.isPresent()) {
            fail("no field " + name);
        }
        return opt.get();
    }
}
