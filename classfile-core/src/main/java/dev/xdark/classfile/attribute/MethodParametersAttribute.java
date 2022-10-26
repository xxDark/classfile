package dev.xdark.classfile.attribute;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * MethodParameters.
 */
public final class MethodParametersAttribute implements Attribute<MethodParametersAttribute> {
    static final Codec<MethodParametersAttribute> CODEC = Codec.of(input -> {
        int length = input.readInt();
        if (length < 2) {
            throw new InvalidAttributeException("Length is less than 2");
        }
        int count = input.readUnsignedShort();
        if (length != 2 + count * 4) {
            throw new InvalidAttributeException("Invalid attribute content");
        }
        return new MethodParametersAttribute(AttributeUtil.readList(input, count, Parameter.CODEC));
    }, (output, value) -> {
        List<Parameter> parameters = value.getParameters();
        output.writeInt(2 + parameters.size() * 4);
        AttributeUtil.writeList(output, parameters, Parameter.CODEC);
    }, Skip.u32());
    private final List<Parameter> parameters;

    /**
     * @param parameters List of parameters.
     */
    public MethodParametersAttribute(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * @return List of parameters.
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public @NotNull AttributeInfo<MethodParametersAttribute> info() {
        return AttributeInfo.MethodParameters;
    }

    public static final class Parameter {
        public static final Codec<Parameter> CODEC = Codec.of(input -> {
            return new Parameter(input.readUnsignedShort(), input.readUnsignedShort());
        }, (output, value) -> {
            output.writeShort(value.getNameIndex());
            output.writeShort(value.getFlags());
        }, Skip.exact(4));
        private final int nameIndex;
        private final int flags;

        /**
         * @param nameIndex Name index.
         * @param flags     Access flags.
         */
        public Parameter(int nameIndex, int flags) {
            this.nameIndex = nameIndex;
            this.flags = flags;
        }

        /**
         * @return Name index.
         */
        public int getNameIndex() {
            return nameIndex;
        }

        /**
         * @return Access flags.
         */
        public int getFlags() {
            return flags;
        }
    }
}
