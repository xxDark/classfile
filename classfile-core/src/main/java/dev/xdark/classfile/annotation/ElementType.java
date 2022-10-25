package dev.xdark.classfile.annotation;

import dev.xdark.classfile.io.Codec;

public final class ElementType<T extends ElementValue<T>> {
    private static final ElementType<?>[] ALL_TYPES = new ElementType['s' + 1];
    public static final ElementType<ElementValueByte> BYTE = make('B', ElementValueByte.CODEC);
    public static final ElementType<ElementValueChar> CHAR = make('C', ElementValueChar.CODEC);
    public static final ElementType<ElementValueDouble> DOUBLE = make('D', ElementValueDouble.CODEC);
    public static final ElementType<ElementValueFloat> FLOAT = make('F', ElementValueFloat.CODEC);
    public static final ElementType<ElementValueInt> INT = make('I', ElementValueInt.CODEC);
    public static final ElementType<ElementValueLong> LONG = make('J', ElementValueLong.CODEC);
    public static final ElementType<ElementValueShort> SHORT = make('S', ElementValueShort.CODEC);
    public static final ElementType<ElementValueBoolean> BOOLEAN = make('Z', ElementValueBoolean.CODEC);
    public static final ElementType<ElementValueString> STRING = make('s', ElementValueString.CODEC);
    public static final ElementType<ElementValueEnum> ENUM = make('e', ElementValueEnum.CODEC);
    public static final ElementType<ElementValueClass> CLASS = make('c', ElementValueClass.CODEC);
    public static final ElementType<ElementValueAnnotation> ANNOTATION = make('@', ElementValueAnnotation.CODEC);
    public static final ElementType<ElementValueArray> ARRAY = make('[', ElementValueArray.CODEC);

    private final int tag;
    private final Codec<T> codec;

    private ElementType(int tag, Codec<T> codec) {
        this.tag = tag;
        this.codec = codec;
    }

    /**
     * @param tag Element value tag.
     * @return Elent value type by it's tag.
     *
     */
    public static <T extends ElementValue<T>> ElementType<T> of(int tag) {
        ElementType<?>[] types;
        if (tag < 0 || tag >= (types = ALL_TYPES).length) {
            return null;
        }
        return (ElementType<T>) types[tag];
    }

    /**
     * @return Element tag.
     */
    public int tag() {
        return tag;
    }

    /**
     * @return Element codec.
     */
    public Codec<T> codec() {
        return codec;
    }

    private static <T extends ElementValue<T>> ElementType<T> make(int tag, Codec<T> codec) {
        ElementType<T> type = new ElementType<>(tag, codec);
        ALL_TYPES[tag] = type;
        return type;
    }
}
