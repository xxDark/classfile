package dev.xdark.classfile;

import dev.xdark.classfile.attribute.CodeAttribute;
import dev.xdark.classfile.attribute.code.FilterInstructionVisitor;
import dev.xdark.classfile.attribute.code.InstructionIO;
import dev.xdark.classfile.attribute.code.InstructionVisitor;
import dev.xdark.classfile.io.Input;
import dev.xdark.classfile.io.buffer.ByteBufferInput;
import dev.xdark.classfile.opcode.Instruction;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
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
        for (MethodNode node : classNode.methods) {
            if ("foo".equals(node.name)) {
                CodeAttribute attribute = node.attributes.stream().map(UnknownStoredAttribute::getAttribute)
                        .filter(x -> x instanceof CodeAttribute)
                        .map(CodeAttribute.class::cast)
                        .findFirst()
                        .orElseThrow(IllegalStateException::new);
                ByteBufferInput input = new ByteBufferInput(ByteBuffer.wrap(attribute.getCode()));
                InstructionIO.read(input, new TrackingInstructionVisitor(input));
            }
        }
    }

    private static void foo() {
        int x = 5;
        int y = 7;
        long v = 1;
        System.out.println("baz");
    }

    private static FieldNode search(List<FieldNode> fieldNodeList, String name) {
        Optional<FieldNode> opt = fieldNodeList.stream().filter(x -> name.equals(x.name)).findFirst();
        if (!opt.isPresent()) {
            fail("no field " + name);
        }
        return opt.get();
    }

    private static final class TrackingInstructionVisitor implements InstructionVisitor {
        private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        private final Input input;
        private int lastPosition;

        public TrackingInstructionVisitor(Input input) {
            this.input = input;
        }

        @Override
        public void visitInstructions() {
        }

        @Override
        public void visitInstruction(@NotNull Instruction<?> instruction) {
            Input input = this.input;
            int position = input.position();
            int lastPosition = this.lastPosition;
            this.lastPosition = position;
            try {
                input.position(lastPosition);
                byte[] buffer = input.read(position - lastPosition);
                input.position(position);
                System.out.println(bytesToHex(buffer) + "  ---  " + instruction.getOpcode().name());
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }

        @Override
        public void visitEnd() {
        }

        private static String bytesToHex(byte[] bytes) {
            StringBuilder builder = new StringBuilder(bytes.length * 3);
            for (int i = 0; i < bytes.length; ) {
                int v = bytes[i] & 0xFF;
                builder.append(HEX_ARRAY[v >>> 4]);
                builder.append(HEX_ARRAY[v & 0x0F]);
                if (++i != bytes.length) {
                    builder.append(' ');
                }
            }
            return builder.toString();
        }
    }
}
