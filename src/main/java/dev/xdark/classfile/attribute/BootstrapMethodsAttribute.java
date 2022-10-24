package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;

import java.util.List;

/**
 * BootstrapMethods.
 */
public final class BootstrapMethodsAttribute implements Attribute<BootstrapMethodsAttribute> {
    static final Codec<BootstrapMethodsAttribute> CODEC = Codec.of(input -> {
        int length = input.readInt();
        if (length < 2) {
            throw new InvalidAttributeException("Length is less than 2");
        }
        int count = input.readUnsignedShort();
        if (length != 2 + count * 6) {
            throw new InvalidAttributeException("Invalid attribute content");
        }
        List<BootstrapMethod> bootstrapMethods = AttributeUtil.readList(input, count, BootstrapMethod.CODEC);
        return new BootstrapMethodsAttribute(bootstrapMethods);
    }, (output, value) -> {
        List<BootstrapMethod> bootstrapMethods = value.getBootstrapMethods();
        int count = bootstrapMethods.size();
        output.writeInt(2 + count * 6);
        AttributeUtil.writeList(output, bootstrapMethods, BootstrapMethod.CODEC);
    });

    private final List<BootstrapMethod> bootstrapMethods;

    /**
     * @param bootstrapMethods Bootstrap methods.
     */
    public BootstrapMethodsAttribute(List<BootstrapMethod> bootstrapMethods) {
        this.bootstrapMethods = bootstrapMethods;
    }

    /**
     * @return List of bootstrap methods.
     */
    public List<BootstrapMethod> getBootstrapMethods() {
        return bootstrapMethods;
    }

    @Override
    public AttributeInfo<BootstrapMethodsAttribute> info() {
        return AttributeInfo.BootstrapMethods;
    }

    public static final class BootstrapMethod {
        public static final Codec<BootstrapMethod> CODEC = Codec.of(input -> {
            int referenceIndex = input.readUnsignedShort();
            int argumentCount = input.readUnsignedShort();
            /*
            if (argumentCount > 255) {
                throw new InvalidAttributeException("Too many arguments");
            }
            */
            int[] indices = AttributeUtil.readUnsignedShorts(input, argumentCount);
            return new BootstrapMethod(referenceIndex, indices);
        }, (output, value) -> {
            output.writeShort(value.getReferenceIndex());
            int[] indices = value.getArgumentIndices();
            AttributeUtil.writeUnsignedShorts(output, indices);
        });
        private final int referenceIndex;
        private final int[] argumentIndices;

        /**
         * @param referenceIndex  Method handle reference index.
         * @param argumentIndices Argument indices.
         */
        public BootstrapMethod(int referenceIndex, int[] argumentIndices) {
            this.referenceIndex = referenceIndex;
            this.argumentIndices = argumentIndices;
        }

        /**
         * @return Method handle index.
         */
        public int getReferenceIndex() {
            return referenceIndex;
        }

        /**
         * @return Argument indices.
         */
        public int[] getArgumentIndices() {
            return argumentIndices;
        }
    }
}
