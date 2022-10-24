package dev.xdark.classfile;

import dev.xdark.classfile.attribute.*;
import dev.xdark.classfile.constantpool.ConstantPool;
import dev.xdark.classfile.constantpool.Tag;
import dev.xdark.classfile.file.*;
import dev.xdark.classfile.io.ContextCodec;
import dev.xdark.classfile.io.buffer.ByteBufferAllocator;
import dev.xdark.classfile.io.buffer.ByteBufferInput;
import dev.xdark.classfile.io.buffer.ByteBufferOutput;
import dev.xdark.classfile.version.ClassVersion;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ClassIOTest {

    @Test
    public void testRead() throws IOException {
        test(Object.class);
        test(System.class);
    }

    private static void test(Class<?> c) throws IOException {
        ClassLoader loader = c.getClassLoader();
        String internalName = c.getName().replace('.', '/');
        String path = internalName + ".class";
        try (InputStream in = loader == null ? ClassLoader.getSystemResourceAsStream(path) : loader.getResourceAsStream(path)) {
            Objects.requireNonNull(in);
            byte[] buf = new byte[1024];
            ByteBuffer buffer = ByteBuffer.allocate(in.available());
            int r;
            while ((r = in.read(buf)) != -1) {
                buffer.put(buf, 0, r);
            }
            buffer.flip();
            ClassIO.read(new ByteBufferInput(buffer), new FilterClassVisitor() {
                private ClassContext classContext;

                @Override
                public void visit(ClassVersion version, ConstantPool constantPool, AccessFlag access, int thisClass, int superClass, int[] interfaces) {
                    assertEquals(ClassIO.getClassName(constantPool, thisClass), internalName);
                    if (superClass != 0) {
                        assertEquals(ClassIO.getClassName(constantPool, superClass), c.getSuperclass().getName().replace('.', '/'));
                    }
                    classContext = new ClassContext(version, constantPool);
                }

                @Override
                public AttributeVisitor visitAttributes() {
                    return new AttributeVerifier(super.visitAttributes(), classContext);
                }

                @Override
                public MethodVisitor visitMethod(AccessFlag access, int nameIndex, int descriptorIndex) {
                    return new FilterMethodVisitor(super.visitMethod(access, nameIndex, descriptorIndex)) {
                        @Override
                        public AttributeVisitor visitAttributes() {
                            return new AttributeVerifier(super.visitAttributes(), classContext);
                        }
                    };
                }

                @Override
                public FieldVisitor visitField(AccessFlag access, int nameIndex, int descriptorIndex) {
                    return new FilterFieldVisitor(super.visitField(access, nameIndex, descriptorIndex)) {
                        @Override
                        public AttributeVisitor visitAttributes() {
                            return new AttributeVerifier(super.visitAttributes(), classContext);
                        }
                    };
                }
            });
            assertFalse(buffer.hasRemaining());
            buffer.flip();
            ByteBufferOutput output = new ByteBufferOutput(ByteBufferAllocator.HEAP);
            ClassWriter writer = new ClassWriter(output);
            ClassIO.read(new ByteBufferInput(buffer.slice()), writer);
            ByteBuffer result = output.consume();
            assertEquals(buffer, result);
        }
    }

    private static final class AttributeVerifier extends FilterAttributeVisitor {
        private final ClassContext classContext;

        AttributeVerifier(AttributeVisitor av, ClassContext classContext) {
            super(av);
            this.classContext = classContext;
        }

        @Override
        public void visitAttribute(int nameIndex, Attribute<?> attribute) {
            if (attribute instanceof UnknownAttribute) {
                if (AttributeInfo.byName(classContext.getConstantPool().get(nameIndex, Tag.CONSTANT_Utf8).value()).known() != null) {
                    fail("Attribute reader produced corrupted attribute");
                }
            }
            super.visitAttribute(nameIndex, attribute);
            ByteBufferOutput output = new ByteBufferOutput(ByteBufferAllocator.HEAP);
            try {
                ContextCodec<Attribute<?>, ClassContext, ClassContext> codec = (ContextCodec<Attribute<?>, ClassContext, ClassContext>) attribute.info().codec();
                codec.write(output, attribute, classContext);
                ByteBuffer buffer = output.consume();
                Attribute<?> reread = codec.read(new ByteBufferInput(buffer.slice()), classContext);
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    }

    public static byte[] filter(byte[] input) {
        ByteBufferOutput output = new ByteBufferOutput(ByteBufferAllocator.HEAP);
        ClassWriter writer = new ClassWriter(output);
        try {
            ClassIO.read(new ByteBufferInput(ByteBuffer.wrap(input)), new FilterClassVisitor(writer) {
                @Override
                public AttributeVisitor visitAttributes() {
                    return new IllegalAttributeRemoverVisitor(super.visitAttributes(), AttributeLocation.CLASS);
                }

                @Override
                public MethodVisitor visitMethod(AccessFlag access, int nameIndex, int descriptorIndex) {
                    return new FilterMethodVisitor(super.visitMethod(access, nameIndex, descriptorIndex)) {
                        @Override
                        public AttributeVisitor visitAttributes() {
                            return new IllegalAttributeRemoverVisitor(super.visitAttributes(), AttributeLocation.METHOD);
                        }
                    };
                }

                @Override
                public FieldVisitor visitField(AccessFlag access, int nameIndex, int descriptorIndex) {
                    return new FilterFieldVisitor(super.visitField(access, nameIndex, descriptorIndex)) {
                        @Override
                        public AttributeVisitor visitAttributes() {
                            return new IllegalAttributeRemoverVisitor(super.visitAttributes(), AttributeLocation.FIELD);
                        }
                    };
                }
            });
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        ByteBuffer buffer = output.consume();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return bytes;
    }

    public static final class IllegalAttributeRemoverVisitor extends FilterAttributeVisitor {
        private final AttributeLocation location;

        public IllegalAttributeRemoverVisitor(AttributeVisitor av, AttributeLocation location) {
            super(av);
            this.location = location;
        }

        @Override
        public void visitAttribute(int nameIndex, Attribute<?> attribute) {
            KnownInfo known = attribute.info().known();
            if (known == null || !known.locations().contains(location)) {
                return;
            }
            super.visitAttribute(nameIndex, attribute);
        }
    }
}
