package dev.xdark.classfile;

import dev.xdark.classfile.constantpool.ConstantPool;
import org.jetbrains.annotations.NotNull;

/**
 * Class context for context-aware codecs.
 *
 * @author xDark
 */
public final class ClassContext {
    private final ClassVersion classVersion;
    private final ConstantPool constantPool;

    public ClassContext(ClassVersion classVersion, ConstantPool constantPool) {
        this.classVersion = classVersion;
        this.constantPool = constantPool;
    }

    /**
     * @return Class version.
     */
    @NotNull
    public ClassVersion getClassVersion() {
        return classVersion;
    }

    /**
     * @return Constant pool.
     */
    @NotNull
    public ConstantPool getConstantPool() {
        return constantPool;
    }
}
