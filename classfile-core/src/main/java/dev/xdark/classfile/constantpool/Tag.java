package dev.xdark.classfile.constantpool;

import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.Nullable;

public final class Tag<T extends ConstantEntry<T>> {

    private static final Tag<?>[] ALL_TAGS = new Tag[21];
    public static final Tag<ConstantUtf8> CONSTANT_Utf8 = normal("CONSTANT_Utf8", 1, ConstantUtf8.CODEC);
    public static final Tag<ConstantInteger> CONSTANT_Integer = normal("CONSTANT_Integer", 3, ConstantInteger.CODEC);
    public static final Tag<ConstantFloat> CONSTANT_Float = normal("CONSTANT_Float", 4, ConstantFloat.CODEC);
    public static final Tag<ConstantLong> CONSTANT_Long = wide("CONSTANT_Long", 5, ConstantLong.CODEC);
    public static final Tag<ConstantDouble> CONSTANT_Double = wide("CONSTANT_Double", 6, ConstantDouble.CODEC);
    public static final Tag<ConstantClass> CONSTANT_Class = normal("CONSTANT_Class", 7, ConstantClass.CODEC);
    public static final Tag<ConstantString> CONSTANT_String = normal("CONSTANT_String", 8, ConstantString.CODEC);
    public static final Tag<ConstantFieldReference> CONSTANT_Fieldref = normal("CONSTANT_Fieldref", 9, ConstantFieldReference.CODEC);
    public static final Tag<ConstantMethodReference> CONSTANT_Methodref = normal("CONSTANT_Methodref", 10, ConstantMethodReference.CODEC);
    public static final Tag<ConstantInterfaceMethodReference> CONSTANT_InterfaceMethodref = normal("CONSTANT_InterfaceMethodref", 11, ConstantInterfaceMethodReference.CODEC);
    public static final Tag<ConstantNameAndType> CONSTANT_NameAndType = normal("CONSTANT_NameAndType", 12, ConstantNameAndType.CODEC);
    public static final Tag<ConstantethodHandle> CONSTANT_MethodHandle = normal("CONSTANT_MethodHandle", 15, ConstantethodHandle.CODEC);
    public static final Tag<ConstantMethodType> CONSTANT_MethodType = normal("CONSTANT_MethodType", 16, ConstantMethodType.CODEC);
    public static final Tag<ConstantInvokeDynamic> CONSTANT_InvokeDynamic = normal("CONSTANT_InvokeDynamic", 18, ConstantInvokeDynamic.CODEC);
    public static final Tag<ConstantModule> CONSTANT_Module = normal("CONSTANT_Module", 19, ConstantModule.CODEC);
    public static final Tag<ConstantPackage> CONSTANT_Package = normal("CONSTANT_Package", 20, ConstantPackage.CODEC);

    private final String name;
    private final int id;
    private final int size;
    private final Codec<T> codec;

    private Tag(String name, int id, int size, Codec<T> codec) {
        this.name = name;
        this.id = id;
        this.size = size;
        this.codec = codec;
    }

    /**
     * @param id Tag ID.
     * @return Tag by it's id or {@code null},
     * if tag is unknown.
     */
    @Nullable
    public static Tag<?> of(int id) {
        Tag<?>[] tags = ALL_TAGS;
        if (id < 0 || id >= tags.length) {
            return null;
        }
        return tags[id];
    }

    /**
     * @return Tag name.
     */
    public String name() {
        return name;
    }

    /**
     * @return ID.
     */
    public int id() {
        return id;
    }

    /**
     * @return Entry size.
     */
    public int size() {
        return size;
    }

    /**
     * @return Codec.
     */
    public Codec<T> codec() {
        return codec;
    }

    @Override
    public String toString() {
        return "tag{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    private static <T extends ConstantEntry<T>> Tag<T> normal(String name, int id, Codec<T> codec) {
        return record(new Tag<>(name, (byte) id, 1, codec));
    }

    private static <T extends ConstantEntry<T>> Tag<T> wide(String name, int id, Codec<T> codec) {
        return record(new Tag<>(name, (byte) id, 2, codec));
    }

    private static <T extends ConstantEntry<T>> Tag<T> record(Tag<T> tag) {
        ALL_TAGS[tag.id()] = tag;
        return tag;
    }
}
