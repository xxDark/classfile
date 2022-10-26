package dev.xdark.classfile;

import org.jetbrains.annotations.NotNull;

/**
 * Upgrades {@link ClassVersion#V0} to {@link ClassVersion#V1}.
 *
 * @author xDark
 */
public final class OakVersionPatcher extends FilterClassVisitor {

    public OakVersionPatcher(@NotNull ClassVisitor cv) {
        super(cv);
    }

    @Override
    public void visit(@NotNull ClassVersion version, @NotNull AccessFlag access, int thisClass, int superClass, int[] interfaces) {
        if (version == ClassVersion.V0) {
            version = ClassVersion.V1;
        }
        super.visit(version, access, thisClass, superClass, interfaces);
    }
}
