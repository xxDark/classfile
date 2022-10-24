package dev.xdark.classfile.pseuo;

import dev.xdark.classfile.opcode.JvmOpcodes;

/**
 * Method instruction node.
 *
 * @author xDark
 */
public final class MethodInstructionNode extends InstructionNode {
    public String owner;
    public String name;
    public String desc;
    public boolean itf;

    /**
     * @param opcode Invocation opcode.
     * @param owner  Owner name.
     * @param name   Method name.
     * @param desc   Method descriptor.
     * @param itf    Whether the owner is an interface.
     */
    public MethodInstructionNode(int opcode, String owner, String name, String desc, boolean itf) {
        super(verifyRange(JvmOpcodes.INVOKEVIRTUAL, opcode, JvmOpcodes.INVOKEINTERFACE));
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.itf = itf;
    }
}
