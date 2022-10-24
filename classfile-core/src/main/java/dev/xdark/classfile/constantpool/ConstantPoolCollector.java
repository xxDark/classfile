package dev.xdark.classfile.constantpool;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Collects constants into collection.
 *
 * @author xDark
 */
public final class ConstantPoolCollector implements ConstantPoolVisitor {
    private final Collection<ConstantEntry<?>> entries;

    /**
     * @param entries Output collection.
     */
    public ConstantPoolCollector(Collection<ConstantEntry<?>> entries) {
        this.entries = entries;
    }

    @Override
    public void visitConstants() {
    }

    @Override
    public void visitConstant(@NotNull ConstantEntry<?> entry) {
        entries.add(entry);
    }

    @Override
    public void visitEnd() {
    }
}
