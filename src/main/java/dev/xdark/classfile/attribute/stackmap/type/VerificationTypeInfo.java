package dev.xdark.classfile.attribute.stackmap.type;

/**
 * Verification type info.
 *
 * @author xDark
 */
public interface VerificationTypeInfo<SELF extends VerificationTypeInfo<SELF>> {

    /**
     * @return Verification type.
     */
    VerificationType<SELF> type();
}
