package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Input;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Annotation IO.
 *
 * @author xDark
 */
public final class AnnotationIO {
    private AnnotationIO() {
    }

    /**
     * Reads an annotation.
     *
     * @param input   Input to read annotation from.
     * @param visitor Annotation visitor.
     * @throws IOException If any I/O error occurs.
     */
    public static void read(@NotNull Input input, @NotNull AnnotationVisitor visitor) throws IOException {
        int typeIndex = input.readUnsignedShort();
        int pairCount = input.readUnsignedShort();
        visitor.visitAnnotation(typeIndex, pairCount);
        for (int i = 0; i < pairCount; i++) {
            int nameIndex = input.readUnsignedShort();
            int elementTag = input.readUnsignedByte();
            ElementType<?> type = ElementType.of(elementTag);
            if (type == null) {
                throw new InvalidAnnotationException("Unknown type " + (char) elementTag);
            }
            if (type == ElementType.ANNOTATION) {
                AnnotationVisitor nested = visitor.visitNestedAnnotation(nameIndex);
                if (nested == null) {
                    skipAnnotation(input);
                } else {
                    read(input, nested);
                }
            } else if (type == ElementType.ARRAY) {
                AnnotationArrayVisitor nested = visitor.visitArray(nameIndex);
                if (nested == null) {
                    skipArray(input);
                } else {
                    read(input, nested);
                }
            } else if (type == ElementType.ENUM) {
                visitor.visitEnum(nameIndex, ElementType.ENUM.codec().read(input));
            } else {
                visitor.visitConstant(nameIndex, (ElementValueConstant<?>) type.codec().read(input));
            }
        }
        visitor.visitEnd();
    }

    private static void read(Input input, AnnotationArrayVisitor visitor) throws IOException {
        int length = input.readUnsignedShort();
        visitor.visitArray(length);
        for (int i = 0; i < length; i++) {
            int elementTag = input.readUnsignedByte();
            ElementType<?> type = ElementType.of(elementTag);
            if (type == null) {
                throw new InvalidAnnotationException("Unknown type " + (char) elementTag);
            }
            if (type == ElementType.ANNOTATION) {
                AnnotationVisitor nested = visitor.visitAnnotation();
                if (nested == null) {
                    skipAnnotation(input);
                } else {
                    read(input, nested);
                }
            } else if (type == ElementType.ARRAY) {
                throw new InvalidAnnotationException("Cannot have multi-dimensional annotations");
            } else if (type == ElementType.ENUM) {
                visitor.visitEnum(ElementType.ENUM.codec().read(input));
            } else {
                visitor.visitConstant((ElementValueConstant<?>) type.codec().read(input));
            }
        }
        visitor.visitEnd();
    }

    private static void skipValue(Input input, int tag) throws IOException {
        switch (tag) {
            case 'e':
                input.skipBytes(4);
                break;
            case '@':
                skipAnnotation(input);
                break;
            case '[':
                skipArray(input);
                break;
            default:
                input.skipBytes(2);
        }
    }

    private static void skipValue(Input input) throws IOException {
        skipValue(input, input.readUnsignedByte());
    }

    private static void skipArray(Input input) throws IOException {
        for (int i = 0, j = input.readUnsignedShort(); i < j; i++) {
            skipValue(input);
        }
    }

    private static void skipAnnotation(Input input) throws IOException {
        input.skipBytes(2);
        for (int i = 0, j = input.readUnsignedShort(); i < j; i++) {
            input.skipBytes(2);
            skipValue(input);
        }
    }
}
