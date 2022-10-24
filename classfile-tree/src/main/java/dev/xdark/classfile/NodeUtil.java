package dev.xdark.classfile;

import dev.xdark.classfile.attribute.AttributeInfo;
import dev.xdark.classfile.attribute.AttributeVisitor;
import dev.xdark.classfile.attribute.SignatureAttribute;
import dev.xdark.classfile.constantpool.ConstantPoolBuilder;

@SuppressWarnings("ConstantConditions")
final class NodeUtil {
    private NodeUtil() {
    }

    static void putSignature(AttributeVisitor av, ConstantPoolBuilder builder, String signature) {
        if (signature != null) {
            int signatureIndex = builder.putUtf8(AttributeInfo.Signature.known().name());
            av.visitAttribute(signatureIndex, new SignatureAttribute(builder.putUtf8(signature)));
        }
    }
}
