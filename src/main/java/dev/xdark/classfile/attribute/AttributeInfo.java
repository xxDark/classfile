package dev.xdark.classfile.attribute;

import dev.xdark.classfile.ClassContext;
import dev.xdark.classfile.attribute.stackmap.StackMapTableAttribute;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.ContextCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static dev.xdark.classfile.attribute.AttributeLocation.*;
import static dev.xdark.classfile.attribute.KnownInfo.kinfo;

/**
 * Attribute info.
 *
 * @author xDark
 */
public final class AttributeInfo<T extends Attribute<T>> {

    private static final Map<String, AttributeInfo<?>> BY_NAME = new HashMap<>();
    public static final AttributeInfo<SourceFileAttribute> SourceFile = info(SourceFileAttribute.CODEC, kinfo("SourceFile", CLASS));
    public static final AttributeInfo<InnerClassesAttribute> InnerClasses = info(InnerClassesAttribute.CODEC, kinfo("InnerClasses", CLASS));
    public static final AttributeInfo<EnclosingMethodAttribute> EnclosingMethod = info(EnclosingMethodAttribute.CODEC, kinfo("EnclosingMethod", CLASS));
    public static final AttributeInfo<SourceDebugExtensionAttribute> SourceDebugExtension = info(SourceDebugExtensionAttribute.CODEC, kinfo("SourceDebugExtension", CLASS));
    public static final AttributeInfo<BootstrapMethodsAttribute> BootstrapMethods = info(BootstrapMethodsAttribute.CODEC, kinfo("BootstrapMethods", CLASS));
    public static final AttributeInfo<ModuleAttribute> Module = info(ModuleAttribute.CODEC, kinfo("Module", CLASS));
    public static final AttributeInfo<ModulePackagesAttribute> ModulePackages = info(ModulePackagesAttribute.CODEC, kinfo("ModulePackages", CLASS));
    public static final AttributeInfo<ModuleMainClassAttribute> ModuleMainClass = info(ModuleMainClassAttribute.CODEC, kinfo("ModuleMainClass", CLASS));
    public static final AttributeInfo<NestHostAttribute> NestHost = info(NestHostAttribute.CODEC, kinfo("NestHost", CLASS));
    public static final AttributeInfo<NestMembersAttribute> NestMembers = info(NestMembersAttribute.CODEC, kinfo("NestMembers", CLASS));
    // TODO Record
    public static final AttributeInfo<PermittedSubclassesAttribute> PermittedSubclasses = info(PermittedSubclassesAttribute.CODEC, kinfo("PermittedSubclasses", CLASS));
    public static final AttributeInfo<ConstantValueAttribute> ConstantValue = info(ConstantValueAttribute.CODEC, kinfo("ConstantValue", FIELD));
    public static final AttributeInfo<CodeAttribute> Code = info(CodeAttribute.CODEC, kinfo("Code", METHOD));
    public static final AttributeInfo<ExceptionsAttribute> Exceptions = info(ExceptionsAttribute.CODEC, kinfo("Exceptions", METHOD));
    // TODO RuntimeVisibleParameterAnnotations, RuntimeInvisibleParameterAnnotations, AnnotationDefault
    public static final AttributeInfo<MethodParametersAttribute> MethodParameters = info(MethodParametersAttribute.CODEC, kinfo("MethodParameters", METHOD));
    public static final AttributeInfo<SyntheticAttribute> Synthetic = info(SyntheticAttribute.CODEC, kinfo("Synthetic", CLASS, METHOD, FIELD));
    public static final AttributeInfo<DeprecatedAttribute> Deprecated = info(DeprecatedAttribute.CODEC, kinfo("Deprecated", CLASS, METHOD, FIELD));
    public static final AttributeInfo<SignatureAttribute> Signature = info(SignatureAttribute.CODEC, kinfo("Signature", CLASS, METHOD, FIELD, RECORD));
    // TODO RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations
    public static final AttributeInfo<LineNumberTableAttribute> LineNumberTable = info(LineNumberTableAttribute.CODEC, kinfo("LineNumberTable", CODE));
    public static final AttributeInfo<LocalVariableTableAttribute> LocalVariableTable = info(LocalVariableTableAttribute.CODEC, kinfo("LocalVariableTable", CODE));
    public static final AttributeInfo<LocalVariableTypeTableAttribute> LocalVariableTypeTable = info(LocalVariableTypeTableAttribute.CODEC, kinfo("LocalVariableTypeTable", CODE));
    public static final AttributeInfo<StackMapTableAttribute> StackMapTable = info(StackMapTableAttribute.CODEC, kinfo("StackMapTable", CODE));

    public static final AttributeInfo<UnknownAttribute> UNKNOWN = info(UnknownAttribute.CODEC, null);

    private final ContextCodec<T, ClassContext, ClassContext> codec;
    private final KnownInfo knownInfo;

    private AttributeInfo(ContextCodec<T, ClassContext, ClassContext> codec, KnownInfo knownInfo) {
        this.codec = codec;
        this.knownInfo = knownInfo;
    }

    /**
     * @param name Attribute name.
     * @return Attribute information.
     */
    @NotNull
    public static <T extends Attribute<T>> AttributeInfo<T> byName(String name) {
        return (AttributeInfo<T>) BY_NAME.getOrDefault(name, UNKNOWN);
    }

    /**
     * @return Attribute codec.
     */
    @NotNull
    public ContextCodec<T, ClassContext, ClassContext> codec() {
        return codec;
    }

    /**
     * @return Known attribute info, if it is defined
     * in the JVM specification.
     */
    @Nullable
    public KnownInfo known() {
        return knownInfo;
    }

    private static <T extends Attribute<T>> AttributeInfo<T> info(ContextCodec<T, ClassContext, ClassContext> codec, KnownInfo knownInfo) {
        AttributeInfo<T> attributeInfo = new AttributeInfo<>(codec, knownInfo);
        if (knownInfo != null) {
            BY_NAME.put(knownInfo.name(), attributeInfo);
        }
        return attributeInfo;
    }

    private static <T extends Attribute<T>> AttributeInfo<T> info(Codec<T> codec, KnownInfo knownInfo) {
        return info(codec.asContextAware(), knownInfo);
    }
}
