package dev.xdark.classfile.pseuo;

import dev.xdark.classfile.opcode.JvmOpcodes;

/**
 * Field instruction node.
 *
 * @author xDark
 */
public final class FieldInstructionNode extends InstructionNode {
    public String owner;
    public String name;
    public String desc;

    /**
     * @param opcode Invocation opcode.
     * @param owner  Owner name.
     * @param name   Field name.
     * @param desc   Field descriptor.
     */
    public FieldInstructionNode(int opcode, String owner, String name, String desc) {
        super(verifyRange(JvmOpcodes.GETSTATIC, opcode, JvmOpcodes.PUTFIELD));
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }
}
