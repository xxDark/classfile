package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * InnerClasses.
 *
 * @author xDark
 */
public final class InnerClassesAttribute implements Attribute<InnerClassesAttribute> {
    static final Codec<InnerClassesAttribute> CODEC = Codec.of(input -> {
        if ((input.readInt() - 2) % 8 != 0) {
            throw new InvalidAttributeException("Invalid inner class list");
        }
        List<InnerClass> innerClasses = AttributeUtil.readList(input, InnerClass.CODEC);
        return new InnerClassesAttribute(innerClasses);
    }, (output, value) -> {
        List<InnerClass> innerClasses = value.getInnerClasses();
        int size = innerClasses.size();
        output.writeInt(size * 8 + 2);
        AttributeUtil.writeList(output, innerClasses, InnerClass.CODEC);
    }, Skip.u32());
    private final List<InnerClass> innerClasses;

    /**
     * @param innerClasses Inner classes.
     */
    public InnerClassesAttribute(List<InnerClass> innerClasses) {
        this.innerClasses = innerClasses;
    }

    /**
     * @return List of inner classes.
     */
    public List<InnerClass> getInnerClasses() {
        return innerClasses;
    }

    @Override
    public @NotNull AttributeInfo<InnerClassesAttribute> info() {
        return AttributeInfo.InnerClasses;
    }

    public static final class InnerClass {

        public static final Codec<InnerClass> CODEC = Codec.of(input -> {
            return new InnerClass(input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort());
        }, (output, value) -> {
            output.writeShort(value.getInnerClassIndex());
            output.writeShort(value.getOuterClassIndex());
            output.writeShort(value.getInnerNameIndex());
            output.writeShort(value.getInnerClassAccessFlags());
        }, Skip.exact(8));
        private final int innerClassIndex;
        private final int outerClassIndex;
        private final int innerNameIndex;
        private final int innerClassAccessFlags;

        /**
         * @param innerClassIndex       Inner class index.
         * @param outerClassIndex       Outer class index.
         * @param innerNameIndex        Inner name index.
         * @param innerClassAccessFlags Inner class access flags.
         */
        public InnerClass(int innerClassIndex, int outerClassIndex, int innerNameIndex, int innerClassAccessFlags) {
            this.innerClassIndex = innerClassIndex;
            this.outerClassIndex = outerClassIndex;
            this.innerNameIndex = innerNameIndex;
            this.innerClassAccessFlags = innerClassAccessFlags;
        }

        /**
         * @return Inner class index.
         */
        public int getInnerClassIndex() {
            return innerClassIndex;
        }

        /**
         * @return Outer class index.
         */
        public int getOuterClassIndex() {
            return outerClassIndex;
        }

        /**
         * @return Inner name index.
         */
        public int getInnerNameIndex() {
            return innerNameIndex;
        }

        /**
         * @return Inner class access flags.
         */
        public int getInnerClassAccessFlags() {
            return innerClassAccessFlags;
        }
    }
}
