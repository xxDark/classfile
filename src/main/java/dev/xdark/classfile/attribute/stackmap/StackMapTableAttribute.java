package dev.xdark.classfile.attribute.stackmap;

import dev.xdark.classfile.attribute.Attribute;
import dev.xdark.classfile.attribute.AttributeInfo;
import dev.xdark.classfile.attribute.stackmap.frame.StackMapFrame;
import dev.xdark.classfile.attribute.stackmap.frame.StackMapFrameType;
import dev.xdark.classfile.io.Codec;

import java.util.ArrayList;
import java.util.List;

/**
 * StackMapTable.
 *
 * @author xDark
 */
public final class StackMapTableAttribute implements Attribute<StackMapTableAttribute> {
    public static final Codec<StackMapTableAttribute> CODEC = Codec.of(input -> {
        input.skipBytes(4);
        int count = input.readUnsignedShort();
        List<StackMapFrame<?>> frames = new ArrayList<>(Math.min(32, count)); // TODO expose AttributeUtil?
        while (count-- != 0) {
            int position = input.position();
            int type = input.readUnsignedByte();
            input.position(position);
            StackMapFrame<?> frame = StackMapFrameType.of(type).codec().read(input);
            frames.add(frame);
        }
        return new StackMapTableAttribute(frames);
    }, (output, value) -> {
        int position = output.position();
        output.writeInt(0);
        List<StackMapFrame<?>> frames = value.getFrames();
        int count = frames.size();
        output.writeShort(count);
        for (int i = 0; i < count; i++) {
            StackMapFrame<?> frame = frames.get(i);
            ((Codec) frame.type().codec()).write(output, frame);
        }
        int newPosition = output.position();
        output.position(position).writeInt(newPosition - position - 4);
        output.position(newPosition);
    });
    private final List<StackMapFrame<?>> frames;

    /**
     * @param frames StackMap frames.
     */
    public StackMapTableAttribute(List<StackMapFrame<?>> frames) {
        this.frames = frames;
    }

    /**
     * @return StackMap frames.
     */
    public List<StackMapFrame<?>> getFrames() {
        return frames;
    }

    @Override
    public AttributeInfo<StackMapTableAttribute> info() {
        return AttributeInfo.StackMapTable;
    }
}
