package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.attribute.code.CodeAdapter;
import dev.xdark.classfile.attribute.code.CodeBuilder;
import dev.xdark.classfile.attribute.code.Label;
import dev.xdark.classfile.constantpool.ConstantPoolBuilder;
import dev.xdark.classfile.io.buffer.ByteBufferAllocator;
import dev.xdark.classfile.io.buffer.ByteBufferOutput;
import dev.xdark.classfile.method.MethodVisitor;
import dev.xdark.classfile.opcode.Opcode;
import org.junit.jupiter.api.Test;
import sun.net.www.HeaderParser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClassWriteTest {

    @Test
    public void testClassCreation() throws IOException {
        ClassWriter writer = new ClassWriter();
        ConstantPoolBuilder cp = writer.visitConstantPool();
        writer.visit(ClassVersion.V8, AccessFlag.ACC_PUBLIC, cp.putClass("Test"), cp.putClass("java/lang/Object"), new int[0]);
        MethodVisitor mv = writer.visitMethod(AccessFlag.ACC_PUBLIC.or(AccessFlag.ACC_STATIC), cp.putUtf8("main"), cp.putUtf8("([Ljava/lang/String;)V"));
        AttributeVisitor attributes = mv.visitAttributes();
        CodeBuilder codeBuilder = new CodeBuilder(ClassVersion.V8);
        CodeAdapter codeAdapter = new CodeAdapter(codeBuilder, cp);
        codeAdapter.visitMethodInsn(Opcode.INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        codeAdapter.visitInsn(Opcode.L2I);
        Label label = new Label();
        codeAdapter.visitJumpInsn(Opcode.IFNE, label);
        codeAdapter.visitInsn(Opcode.RETURN);
        codeAdapter.visitLabel(label);
        codeAdapter.visitFieldInsn(Opcode.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        codeAdapter.visitUtf8("Hello, World!");
        codeAdapter.visitMethodInsn(Opcode.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        codeAdapter.visitInsn(Opcode.RETURN);
        codeAdapter.visitMaxs(2, 1);
        codeAdapter.visitEnd();
        attributes.visitAttribute(cp.putUtf8("Code"), codeBuilder.create());
        attributes.visitEnd();
        mv.visitEnd();
        ByteBufferOutput output = new ByteBufferOutput(ByteBufferAllocator.HEAP);
        writer.writeTo(output);
        if (false) {
            ByteBuffer buf = output.consume();
            byte[] b = new byte[buf.remaining()];
            buf.get(b);
            Path p = Paths.get("Test.class").toAbsolutePath();
            System.out.println(p);
            Files.write(p, b);
        }
    }
}
