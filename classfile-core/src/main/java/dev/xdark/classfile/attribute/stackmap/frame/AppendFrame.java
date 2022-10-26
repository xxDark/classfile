package dev.xdark.classfile.attribute.stackmap.frame;

import dev.xdark.classfile.attribute.InvalidAttributeException;
import dev.xdark.classfile.attribute.stackmap.type.VerificationType;
import dev.xdark.classfile.attribute.stackmap.type.VerificationTypeInfo;
import dev.xdark.classfile.io.Codec;

import java.util.ArrayList;
import java.util.List;

/**
 * Same locals as the previous frame except K locals are added, the stack is empty.
 * Offset delta is equal to the tag.
 * K is calculated by {@code frameType - 251}.
 *
 * @author xDark
 */
public final class AppendFrame extends StackMapFrame<AppendFrame> {
    static final Codec<AppendFrame> CODEC = Codec.of(input -> {
        int type = input.readUnsignedByte();
        if (!FrameTypeRange.CHOP.includes(type)) {
            throw new InvalidAttributeException("Tag mismatch");
        }
        int offsetDelta = input.readUnsignedShort();
        int verificationTypeCount = type - 251;
        List<VerificationTypeInfo<?>> verificationTypeInfos = new ArrayList<>(verificationTypeCount);
        while (verificationTypeCount-- != 0) {
            int tag = input.readUnsignedByte();
            VerificationType<?> verificationType = VerificationType.of(tag);
            if (verificationType == null) {
                throw new InvalidAttributeException("Unknown verification type " + tag);
            }
            verificationTypeInfos.add(verificationType.codec().read(input));
        }
        return new AppendFrame(type, offsetDelta, verificationTypeInfos);
    }, (output, value) -> {
        output.writeByte(value.getType());
        output.writeShort(value.getOffsetDelta());
        List<VerificationTypeInfo<?>> verificationTypeInfos = value.getVerificationTypeInfos();
        output.writeShort(verificationTypeInfos.size());
        for (VerificationTypeInfo<?> info : verificationTypeInfos) {
            VerificationType<?> type = info.type();
            output.writeByte(type.tag());
            ((Codec) type.codec()).write(output, info);
        }
    }, input -> {
        int type = input.readUnsignedByte();
        if (!FrameTypeRange.CHOP.includes(type)) {
            throw new InvalidAttributeException("Tag mismatch");
        }
        input.skipBytes(2);
        int verificationTypeCount = type - 251;
        while (verificationTypeCount-- != 0) {
            int tag = input.readUnsignedByte();
            VerificationType<?> verificationType = VerificationType.of(tag);
            if (verificationType == null) {
                throw new InvalidAttributeException("Unknown verification type " + tag);
            }
            verificationType.codec().skip(input);
        }
    });

    private final int tag;
    private final List<VerificationTypeInfo<?>> verificationTypeInfos;

    /**
     * @param tag                   Frame tag.
     * @param offsetDelta           Offset delta.
     * @param verificationTypeInfos Verification type infos.
     */
    public AppendFrame(int tag, int offsetDelta, List<VerificationTypeInfo<?>> verificationTypeInfos) {
        super(offsetDelta);
        this.tag = tag;
        this.verificationTypeInfos = verificationTypeInfos;
    }

    /**
     * @return Verification type infos.
     */
    public List<VerificationTypeInfo<?>> getVerificationTypeInfos() {
        return verificationTypeInfos;
    }

    @Override
    public int getType() {
        return tag;
    }

    @Override
    public StackMapFrameType<AppendFrame> type() {
        return StackMapFrameType.APPEND;
    }
}
