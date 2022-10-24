package dev.xdark.classfile.dynamic;

/**
 * MethodHandle reference kinds.
 *
 * @author xDark
 */
public interface ReferenceKind {
    int REF_GETFIELD = 1;
    int REF_GETSTATIC = 2;
    int REF_PUTFIELD = 3;
    int REF_PUTSTATIC = 4;
    int REF_INVOKEVIRTUAL = 5;
    int REF_INVOKESTATIC = 6;
    int REF_INVOKESPECIAL = 7;
    int REF_NEWINVOKESPECIAL = 8;
    int REF_INVOKEINTERFACE = 9;
}
