package dev.xdark.classfile.attribute.type;

import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Skip;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * localvar_target.
 *
 * @author xDark
 */
public final class LocalVariableTargetInfo implements TargetInfo<LocalVariableTargetInfo> {
    static final Codec<LocalVariableTargetInfo> CODEC = Codec.of(input -> {
        TargetType<LocalVariableTargetInfo> type = TargetType.require(input);
        int length = input.readUnsignedShort();
        List<LocalVariable> localVariables = new ArrayList<>(Math.min(32, length));
        while (length-- != 0) {
            localVariables.add(LocalVariable.CODEC.read(input));
        }
        return new LocalVariableTargetInfo(type, localVariables);
    }, (output, value) -> {
        output.writeByte(value.type().kind());
        List<LocalVariable> localVariables = value.getLocalVariables();
        int count = localVariables.size();
        output.writeShort(count);
        for (int i = 0; i < count; i++) {
            LocalVariable.CODEC.write(output, localVariables.get(i));
        }
    }, input -> {
        input.skipBytes(1);
        for (int i = 0, j = input.readUnsignedShort(); i < j; i++) {
            LocalVariable.CODEC.skip(input);
        }
    });
    private final TargetType<LocalVariableTargetInfo> type;
    private final List<LocalVariable> localVariables;

    /**
     * @param type           Target type.
     * @param localVariables List of local variables.
     */
    public LocalVariableTargetInfo(TargetType<LocalVariableTargetInfo> type, List<LocalVariable> localVariables) {
        this.type = type;
        this.localVariables = localVariables;
    }

    /**
     * @return List of local variables.
     */
    public List<LocalVariable> getLocalVariables() {
        return localVariables;
    }

    @Override
    public @NotNull TargetType<LocalVariableTargetInfo> type() {
        return type;
    }

    public static final class LocalVariable {
        static final Codec<LocalVariable> CODEC = Codec.of(input -> {
            return new LocalVariable(input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort());
        }, (output, value) -> {
            output.writeShort(value.getStart());
            output.writeShort(value.getLength());
            output.writeShort(value.getIndex());
        }, Skip.exact(6));
        private final int start;
        private final int length;
        private final int index;

        /**
         * @param start  Start pc.
         * @param length Length for which the variable lasts.
         * @param index  Local variable index.
         */
        public LocalVariable(int start, int length, int index) {
            this.start = start;
            this.length = length;
            this.index = index;
        }

        /**
         * @return Start pc.
         */
        public int getStart() {
            return start;
        }

        /**
         * @return Length for which the variable lasts.
         */
        public int getLength() {
            return length;
        }

        /**
         * @return Variable index.
         */
        public int getIndex() {
            return index;
        }
    }
}
