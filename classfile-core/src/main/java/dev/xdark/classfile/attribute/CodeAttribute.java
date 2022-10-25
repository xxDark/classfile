package dev.xdark.classfile.attribute;

import dev.xdark.classfile.ClassContext;
import dev.xdark.classfile.attribute.code.Label;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.ContextCodec;
import dev.xdark.classfile.ClassVersion;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Code.
 *
 * @author xDark
 */
public final class CodeAttribute implements Attribute<CodeAttribute> {
    static final ContextCodec<CodeAttribute, ClassContext, ClassContext> CODEC = ContextCodec.of((input, ctx) -> {
        input.skipBytes(4);
        int maxStack, maxLocals, codeLength;
        if (ctx.getClassVersion() != ClassVersion.V0) {
            maxStack = input.readUnsignedShort();
            maxLocals = input.readUnsignedShort();
            codeLength = input.readInt();
        } else {
            maxStack = input.readUnsignedByte();
            maxLocals = input.readUnsignedByte();
            codeLength = input.readUnsignedShort();
        }
        byte[] code = input.read(codeLength);
        List<TryCatchBlock> tryCatchBlocks = AttributeUtil.readList(input, TryCatchBlock.CODEC);
        List<NamedAttributeInstance<?>> attributes = new ArrayList<>();
        AttributeCollector collector = new AttributeCollector(attributes);
        AttributeIO.read(input, ctx, collector);
        return new CodeAttribute(maxStack, maxLocals, code, tryCatchBlocks, attributes);
    }, (output, value, ctx) -> {
        int position = output.position();
        output.writeInt(0);
        int maxStack = value.getMaxStack(), maxLocals = value.getMaxLocals();
        byte[] code = value.getCode();
        if (ctx.getClassVersion() != ClassVersion.V0) {
            output.writeShort(maxStack);
            output.writeShort(maxLocals);
            output.writeInt(code.length);
        } else {
            output.writeByte(maxStack);
            output.writeByte(maxLocals);
            output.writeShort(code.length);
        }
        output.write(code);
        AttributeUtil.writeList(output, value.getTryCatchBlocks(), TryCatchBlock.CODEC);
        List<NamedAttributeInstance<?>> attributes = value.getAttributes();
        output.writeShort(attributes.size());
        for (int i = 0, j = attributes.size(); i < j; i++) {
            NamedAttributeInstance<?> attr = attributes.get(i);
            AttributeIO.write(output, attr.getNameIndex(), ctx, attr.getAttribute());
        }
        int newPosition = output.position();
        output.position(position).writeInt(newPosition - position - 4);
        output.position(newPosition);
    });
    private final int maxStack;
    private final int maxLocals;
    private final byte[] code;
    private final List<TryCatchBlock> tryCatchBlocks;
    private final List<NamedAttributeInstance<?>> attributes;

    /**
     * @param maxStack       Max stack.
     * @param maxLocals      Max locals.
     * @param code           Code.
     * @param tryCatchBlocks Exception table.
     * @param attributes     Nested attributes.
     */
    public CodeAttribute(int maxStack, int maxLocals, byte[] code, List<TryCatchBlock> tryCatchBlocks, List<NamedAttributeInstance<?>> attributes) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.code = code;
        this.tryCatchBlocks = tryCatchBlocks;
        this.attributes = attributes;
    }

    /**
     * @return Max stack.
     */
    public int getMaxStack() {
        return maxStack;
    }

    /**
     * @return Max locals.
     */
    public int getMaxLocals() {
        return maxLocals;
    }

    /**
     * @return Code.
     */
    public byte[] getCode() {
        return code;
    }

    /**
     * @return Try/catch blocks.
     */
    public List<TryCatchBlock> getTryCatchBlocks() {
        return tryCatchBlocks;
    }

    /**
     * @return Nested attributes.
     */
    public List<NamedAttributeInstance<?>> getAttributes() {
        return attributes;
    }

    @Override
    public @NotNull AttributeInfo<CodeAttribute> info() {
        return AttributeInfo.Code;
    }

    public static final class TryCatchBlock {
        public static final Codec<TryCatchBlock> CODEC = Codec.of(input -> {
            return new TryCatchBlock(Label.exact(input.readUnsignedShort()), Label.exact(input.readUnsignedShort()), Label.exact(input.readUnsignedShort()), input.readUnsignedShort());
        }, (output, value) -> {
            output.writeShort(value.getStart().getPosition());
            output.writeShort(value.getEnd().getPosition());
            output.writeShort(value.getHandler().getPosition());
            output.writeShort(value.getTypeIndex());
        });
        private final Label start;
        private final Label end;
        private final Label handler;
        private final int typeIndex;

        /**
         * @param start     Start pc.
         * @param end       End pc.
         * @param handler   Handler pc.
         * @param typeIndex Type index.
         */
        public TryCatchBlock(Label start, Label end, Label handler, int typeIndex) {
            this.start = start;
            this.end = end;
            this.handler = handler;
            this.typeIndex = typeIndex;
        }

        /**
         * @return Start pc.
         */
        public Label getStart() {
            return start;
        }

        /**
         * @return End pc.
         */
        public Label getEnd() {
            return end;
        }

        /**
         * @return Handler pc.
         */
        public Label getHandler() {
            return handler;
        }

        /**
         * @return Type index.
         */
        public int getTypeIndex() {
            return typeIndex;
        }
    }

    private static <T extends Throwable> void sneaky(Throwable t) throws T {
        throw (T) t;
    }
}
