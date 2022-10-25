package dev.xdark.classfile.attribute;

import dev.xdark.classfile.ClassContext;
import dev.xdark.classfile.io.ContextCodec;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Record.
 *
 * @author xDark
 */
public final class RecordAttribute implements Attribute<RecordAttribute> {
    static final ContextCodec<RecordAttribute, ClassContext, ClassContext> CODEC = ContextCodec.of((input, ctx) -> {
        int length = input.readInt();
        if (length < 2) {
            throw new InvalidAttributeException("Length is less than 2");
        }
        int count = input.readUnsignedShort();
        if (length < 2 + count * 6) {
            throw new InvalidAttributeException("Invalid attribute content");
        }
        return new RecordAttribute(AttributeUtil.readList(input, count, ctx, RecordComponent.CODEC));
    }, (output, value, ctx) -> {
        int position = output.position();
        output.writeInt(0);
        AttributeUtil.writeList(output, value.getComponents(), ctx, RecordComponent.CODEC);
        int newPosition = output.position();
        output.position(position).writeInt(newPosition - position - 4);
        output.position(newPosition);
    });
    private final List<RecordComponent> components;

    /**
     * @param components List of components.
     */
    public RecordAttribute(List<RecordComponent> components) {
        this.components = components;
    }

    /**
     * @return List of components.
     */
    public List<RecordComponent> getComponents() {
        return components;
    }

    @Override
    public @NotNull AttributeInfo<RecordAttribute> info() {
        return AttributeInfo.Record;
    }

    public static final class RecordComponent {
        static final ContextCodec<RecordComponent, ClassContext, ClassContext> CODEC = ContextCodec.of((input, ctx) -> {
            int nameIndex = input.readUnsignedShort();
            int descriptorIndex = input.readUnsignedShort();
            List<NamedAttributeInstance<?>> attributes = new ArrayList<>();
            AttributeCollector collector = new AttributeCollector(attributes);
            AttributeIO.read(input, ctx, collector);
            return new RecordComponent(nameIndex, descriptorIndex, attributes);
        }, (output, value, ctx) -> {
            output.writeShort(value.getNameIndex());
            output.writeShort(value.getDescriptorIndex());
            List<NamedAttributeInstance<?>> attributes = value.getAttributes();
            output.writeShort(attributes.size());
            for (int i = 0, j = attributes.size(); i < j; i++) {
                NamedAttributeInstance<?> attr = attributes.get(i);
                AttributeIO.write(output, attr.getNameIndex(), ctx, attr.getAttribute());
            }
        });
        private final int nameIndex;
        private final int descriptorIndex;
        private final List<NamedAttributeInstance<?>> attributes;

        /**
         * @param nameIndex       Name index.
         * @param descriptorIndex Descriptor index.
         * @param attributes      Attributes.
         */
        public RecordComponent(int nameIndex, int descriptorIndex, List<NamedAttributeInstance<?>> attributes) {
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.attributes = attributes;
        }

        /**
         * @return Name index.
         */
        public int getNameIndex() {
            return nameIndex;
        }

        /**
         * @return Descriptor index.
         */
        public int getDescriptorIndex() {
            return descriptorIndex;
        }

        /**
         * @return Attributes.
         */
        public List<NamedAttributeInstance<?>> getAttributes() {
            return attributes;
        }
    }
}
