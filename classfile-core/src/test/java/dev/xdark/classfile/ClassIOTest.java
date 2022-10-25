package dev.xdark.classfile;

import dev.xdark.classfile.attribute.*;
import dev.xdark.classfile.constantpool.*;
import dev.xdark.classfile.field.FieldVisitor;
import dev.xdark.classfile.field.FilterFieldVisitor;
import dev.xdark.classfile.io.ContextCodec;
import dev.xdark.classfile.io.buffer.ByteBufferAllocator;
import dev.xdark.classfile.io.buffer.ByteBufferInput;
import dev.xdark.classfile.io.buffer.ByteBufferOutput;
import dev.xdark.classfile.method.FilterMethodVisitor;
import dev.xdark.classfile.method.MethodVisitor;
import dev.xdark.classfile.opcode.Opcode;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ClassIOTest {

    @Test
    public void testRead() throws IOException {
        test(Object.class);
        test(System.class);
        test(Retention.class);
        test(Target.class);
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
                private ConstantPool constantPool;
                private ClassContext classContext;

                @Override
                public ConstantPoolVisitor visitConstantPool() {
                    ConstantPoolBuilder builder = new ConstantPoolBuilder();
                    return new FilterConstantPoolVisitor(builder) {
                        @Override
                        public void visitEnd() {
                            constantPool = builder.build();
                        }
                    };
                }

                @Override
                public void visit(@NotNull ClassVersion version, @NotNull AccessFlag access, int thisClass, int superClass, int[] interfaces) {
                    ConstantPool constantPool = this.constantPool;
                    assertEquals(ClassIO.getClassName(constantPool, thisClass), internalName);
                    Class<?> sp = c.getSuperclass();
                    if (superClass != 0 && sp != null /* apparently, getSuperClass will return null for annotations?? */) {
                        assertEquals(ClassIO.getClassName(constantPool, superClass), sp.getName().replace('.', '/'));
                    }
                    classContext = new ClassContext(version, constantPool);
                }

                @Override
                public AttributeVisitor visitAttributes() {
                    AttributeVisitor av = super.visitAttributes();
                    return new AttributeVerifier(av, classContext);
                }

                @Override
                public MethodVisitor visitMethod(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
                    MethodVisitor mv = super.visitMethod(access, nameIndex, descriptorIndex);
                    return new FilterMethodVisitor(mv) {
                        @Override
                        public AttributeVisitor visitAttributes() {
                            return new AttributeVerifier(super.visitAttributes(), classContext);
                        }
                    };
                }

                @Override
                public FieldVisitor visitField(@NotNull AccessFlag access, int nameIndex, int descriptorIndex) {
                    FieldVisitor fv = super.visitField(access, nameIndex, descriptorIndex);
                    return new FilterFieldVisitor(fv) {
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
            ClassWriter writer = new ClassWriter();
            ClassIO.read(new ByteBufferInput(buffer.slice()), writer);
            writer.writeTo(output);
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
        public void visitAttribute(int nameIndex, @NotNull Attribute<?> attribute) {
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
            if (attribute instanceof CodeAttribute) {
                byte[] code = ((CodeAttribute) attribute).getCode();
                ByteBuffer buffer = ByteBuffer.wrap(code);
                ByteBufferInput input = new ByteBufferInput(buffer);
                while (buffer.hasRemaining()) {
                    try {
                        int position = input.position();
                        int raw = input.readUnsignedByte();
                        Opcode<?> opcode = Opcode.of(raw);
                        if (opcode == null) {
                            throw new IOException("Failed to read opcode " + raw);
                        }
                        input.position(position);
                        opcode.codec().read(input);
                    } catch (IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                }
            }
        }
    }
}
