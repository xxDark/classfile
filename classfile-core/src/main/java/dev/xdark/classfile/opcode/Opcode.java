package dev.xdark.classfile.opcode;

import dev.xdark.classfile.InvalidClassException;
import dev.xdark.classfile.io.Codec;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JVM opcode.
 *
 * @author xDark
 */
@SuppressWarnings("unused")
public final class Opcode<T extends Instruction<T>> {
    private static final Map<String, Opcode<?>> BY_NAME = new HashMap<>();
    private static final Opcode<?>[] ALL_OPCODES = new Opcode[203];
    public static final Opcode<EmptyInstruction> NOP = make("nop", JvmOpcodes.NOP, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ACONST_NULL = make("aconst_null", JvmOpcodes.ACONST_NULL, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ICONST_M1 = make("iconst_m1", JvmOpcodes.ICONST_M1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ICONST_0 = make("iconst_0", JvmOpcodes.ICONST_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ICONST_1 = make("iconst_1", JvmOpcodes.ICONST_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ICONST_2 = make("iconst_2", JvmOpcodes.ICONST_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ICONST_3 = make("iconst_3", JvmOpcodes.ICONST_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ICONST_4 = make("iconst_4", JvmOpcodes.ICONST_4, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ICONST_5 = make("iconst_5", JvmOpcodes.ICONST_5, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LCONST_0 = make("lconst_0", JvmOpcodes.LCONST_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LCONST_1 = make("lconst_1", JvmOpcodes.LCONST_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FCONST_0 = make("fconst_0", JvmOpcodes.FCONST_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FCONST_1 = make("fconst_1", JvmOpcodes.FCONST_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FCONST_2 = make("fconst_2", JvmOpcodes.FCONST_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DCONST_0 = make("dconst_0", JvmOpcodes.DCONST_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DCONST_1 = make("dconst_1", JvmOpcodes.DCONST_1, EmptyInstruction.CODEC);
    public static final Opcode<SingedByteInstruction> BIPUSH = make("bipush", JvmOpcodes.BIPUSH, SingedByteInstruction.CODEC);
    public static final Opcode<SignedShortInstruction> SIPUSH = make("sipush", JvmOpcodes.SIPUSH, SignedShortInstruction.CODEC);
    public static final Opcode<UnsignedByteInstruction> LDC = make("ldc", JvmOpcodes.LDC, UnsignedByteInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> LDC_W = make("ldc_w", JvmOpcodes.LDC_W, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> LDC2_W = make("ldc2_w", JvmOpcodes.LDC2_W, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedByteInstruction> ILOAD = make("iload", JvmOpcodes.ILOAD, UnsignedByteInstruction.CODEC);
    public static final Opcode<UnsignedByteInstruction> LLOAD = make("lload", JvmOpcodes.LLOAD, UnsignedByteInstruction.CODEC);
    public static final Opcode<UnsignedByteInstruction> FLOAD = make("fload", JvmOpcodes.FLOAD, UnsignedByteInstruction.CODEC);
    public static final Opcode<UnsignedByteInstruction> DLOAD = make("dload", JvmOpcodes.DLOAD, UnsignedByteInstruction.CODEC);
    public static final Opcode<UnsignedByteInstruction> ALOAD = make("aload", JvmOpcodes.ALOAD, UnsignedByteInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ILOAD_0 = make("iload_0", JvmOpcodes.ILOAD_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ILOAD_1 = make("iload_1", JvmOpcodes.ILOAD_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ILOAD_2 = make("iload_2", JvmOpcodes.ILOAD_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ILOAD_3 = make("iload_3", JvmOpcodes.ILOAD_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LLOAD_0 = make("lload_0", JvmOpcodes.LLOAD_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LLOAD_1 = make("lload_1", JvmOpcodes.LLOAD_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LLOAD_2 = make("lload_2", JvmOpcodes.LLOAD_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LLOAD_3 = make("lload_3", JvmOpcodes.LLOAD_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FLOAD_0 = make("fload_0", JvmOpcodes.FLOAD_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FLOAD_1 = make("fload_1", JvmOpcodes.FLOAD_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FLOAD_2 = make("fload_2", JvmOpcodes.FLOAD_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FLOAD_3 = make("fload_3", JvmOpcodes.FLOAD_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DLOAD_0 = make("dload_0", JvmOpcodes.DLOAD_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DLOAD_1 = make("dload_1", JvmOpcodes.DLOAD_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DLOAD_2 = make("dload_2", JvmOpcodes.DLOAD_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DLOAD_3 = make("dload_3", JvmOpcodes.DLOAD_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ALOAD_0 = make("aload_0", JvmOpcodes.ALOAD_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ALOAD_1 = make("aload_1", JvmOpcodes.ALOAD_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ALOAD_2 = make("aload_2", JvmOpcodes.ALOAD_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ALOAD_3 = make("aload_3", JvmOpcodes.ALOAD_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IALOAD = make("iaload", JvmOpcodes.IALOAD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LALOAD = make("laload", JvmOpcodes.LALOAD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FALOAD = make("faload", JvmOpcodes.FALOAD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DALOAD = make("daload", JvmOpcodes.DALOAD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> AALOAD = make("aaload", JvmOpcodes.AALOAD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> BALOAD = make("baload", JvmOpcodes.BALOAD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> CALOAD = make("caload", JvmOpcodes.CALOAD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> SALOAD = make("saload", JvmOpcodes.SALOAD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ISTORE = make("istore", JvmOpcodes.ISTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LSTORE = make("lstore", JvmOpcodes.LSTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FSTORE = make("fstore", JvmOpcodes.FSTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DSTORE = make("dstore", JvmOpcodes.DSTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ASTORE = make("astore", JvmOpcodes.ASTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ISTORE_0 = make("istore_0", JvmOpcodes.ISTORE_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ISTORE_1 = make("istore_1", JvmOpcodes.ISTORE_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ISTORE_2 = make("istore_2", JvmOpcodes.ISTORE_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ISTORE_3 = make("istore_3", JvmOpcodes.ISTORE_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LSTORE_0 = make("lstore_0", JvmOpcodes.LSTORE_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LSTORE_1 = make("lstore_1", JvmOpcodes.LSTORE_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LSTORE_2 = make("lstore_2", JvmOpcodes.LSTORE_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LSTORE_3 = make("lstore_3", JvmOpcodes.LSTORE_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FSTORE_0 = make("fstore_0", JvmOpcodes.FSTORE_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FSTORE_1 = make("fstore_1", JvmOpcodes.FSTORE_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FSTORE_2 = make("fstore_2", JvmOpcodes.FSTORE_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FSTORE_3 = make("fstore_3", JvmOpcodes.FSTORE_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DSTORE_0 = make("dstore_0", JvmOpcodes.DSTORE_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DSTORE_1 = make("dstore_1", JvmOpcodes.DSTORE_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DSTORE_2 = make("dstore_2", JvmOpcodes.DSTORE_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DSTORE_3 = make("dstore_3", JvmOpcodes.DSTORE_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ASTORE_0 = make("astore_0", JvmOpcodes.ASTORE_0, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ASTORE_1 = make("astore_1", JvmOpcodes.ASTORE_1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ASTORE_2 = make("astore_2", JvmOpcodes.ASTORE_2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ASTORE_3 = make("astore_3", JvmOpcodes.ASTORE_3, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IASTORE = make("iastore", JvmOpcodes.IASTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LASTORE = make("lastore", JvmOpcodes.LASTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FASTORE = make("fastore", JvmOpcodes.FASTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DASTORE = make("dastore", JvmOpcodes.DASTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> AASTORE = make("aastore", JvmOpcodes.AASTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> BASTORE = make("bastore", JvmOpcodes.BASTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> CASTORE = make("castore", JvmOpcodes.CASTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> SASTORE = make("sastore", JvmOpcodes.SASTORE, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> POP = make("pop", JvmOpcodes.POP, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> POP2 = make("pop2", JvmOpcodes.POP2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DUP = make("dup", JvmOpcodes.DUP, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DUP_X1 = make("dup)x1", JvmOpcodes.DUP_X1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DUP_X2 = make("dup_x2", JvmOpcodes.DUP_X2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DUP2 = make("dup2", JvmOpcodes.DUP2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DUP2_X1 = make("dup2_x1", JvmOpcodes.DUP2_X1, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DUP2_X2 = make("dup2_x2", JvmOpcodes.DUP2_X2, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> SWAP = make("swap", JvmOpcodes.SWAP, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IADD = make("iadd", JvmOpcodes.IADD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LADD = make("ladd", JvmOpcodes.LADD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FADD = make("fadd", JvmOpcodes.FADD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DADD = make("dadd", JvmOpcodes.DADD, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ISUB = make("isub", JvmOpcodes.ISUB, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LSUB = make("lsub", JvmOpcodes.LSUB, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FSUB = make("fsub", JvmOpcodes.FSUB, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DSUB = make("dsub", JvmOpcodes.DSUB, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IMUL = make("imul", JvmOpcodes.IMUL, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LMUL = make("lmul", JvmOpcodes.LMUL, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FMUL = make("fmul", JvmOpcodes.FMUL, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DMUL = make("dmul", JvmOpcodes.DMUL, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IDIV = make("idiv", JvmOpcodes.IDIV, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LDIV = make("ldiv", JvmOpcodes.LDIV, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FDIV = make("fdiv", JvmOpcodes.FDIV, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DDIV = make("ddiv", JvmOpcodes.DDIV, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IREM = make("irem", JvmOpcodes.IREM, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LREM = make("lrem", JvmOpcodes.LREM, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FREM = make("frem", JvmOpcodes.FREM, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DREM = make("drem", JvmOpcodes.DREM, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> INEG = make("ineg", JvmOpcodes.INEG, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LNEG = make("lneg", JvmOpcodes.LNEG, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FNEG = make("fneg", JvmOpcodes.FNEG, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DNEG = make("dneg", JvmOpcodes.DNEG, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ISHL = make("ishl", JvmOpcodes.ISHL, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LSHL = make("lshl", JvmOpcodes.LSHL, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ISHR = make("ishr", JvmOpcodes.ISHR, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LSHR = make("lshr", JvmOpcodes.LSHR, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IUSHR = make("iushr", JvmOpcodes.IUSHR, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LUSHR = make("lushr", JvmOpcodes.LUSHR, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IAND = make("iand", JvmOpcodes.IAND, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LAND = make("land", JvmOpcodes.LAND, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IOR = make("ior", JvmOpcodes.IOR, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LOR = make("lor", JvmOpcodes.LOR, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IXOR = make("ixor", JvmOpcodes.IXOR, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LXOR = make("lxor", JvmOpcodes.LXOR, EmptyInstruction.CODEC);
    public static final Opcode<IincInstruction> IINC = make("iinc", JvmOpcodes.IINC, IincInstruction.CODEC);
    public static final Opcode<EmptyInstruction> I2L = make("i2l", JvmOpcodes.I2L, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> I2F = make("i2f", JvmOpcodes.I2F, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> I2D = make("i2d", JvmOpcodes.I2D, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> L2I = make("l2i", JvmOpcodes.L2I, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> L2F = make("l2f", JvmOpcodes.L2F, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> L2D = make("l2d", JvmOpcodes.L2D, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> F2I = make("f2i", JvmOpcodes.F2I, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> F2L = make("f2l", JvmOpcodes.F2L, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> F2D = make("f2d", JvmOpcodes.F2D, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> D2I = make("d2i", JvmOpcodes.D2I, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> D2L = make("d2l", JvmOpcodes.D2L, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> D2F = make("d2f", JvmOpcodes.D2F, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> I2B = make("i2b", JvmOpcodes.I2B, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> I2C = make("i2c", JvmOpcodes.I2C, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> I2S = make("i2s", JvmOpcodes.I2S, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LCMP = make("lcmp", JvmOpcodes.LCMP, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FCMPL = make("fcmpl", JvmOpcodes.FCMPL, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FCMPG = make("fcmpg", JvmOpcodes.FCMPG, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DCMPL = make("dcmpl", JvmOpcodes.DCMPL, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DCMPG = make("dcmpg", JvmOpcodes.DCMPG, EmptyInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IFEQ = make("ifeq", JvmOpcodes.IFEQ, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IFNE = make("ifne", JvmOpcodes.IFNE, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IFLT = make("iflt", JvmOpcodes.IFLT, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IFGE = make("ifge", JvmOpcodes.IFGE, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IFGT = make("ifgt", JvmOpcodes.IFGT, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IFLE = make("ifle", JvmOpcodes.IFLE, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IF_ICMPEQ = make("if_icmpeq", JvmOpcodes.IF_ICMPEQ, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IF_ICMPNE = make("if_icmpne", JvmOpcodes.IF_ICMPNE, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IF_ICMPLT = make("if_icmplt", JvmOpcodes.IF_ICMPLT, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IF_ICMPGE = make("if_icmpge", JvmOpcodes.IF_ICMPGE, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IF_ICMPGT = make("if_icmpgt", JvmOpcodes.IF_ICMPGT, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IF_ICMPLE = make("if_icmple", JvmOpcodes.IF_ICMPLE, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IF_ACMPEQ = make("if_acmpeq", JvmOpcodes.IF_ACMPEQ, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IF_ACMPNE = make("if_acmpne", JvmOpcodes.IF_ACMPNE, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> GOTO = make("goto", JvmOpcodes.GOTO, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> JSR = make("jsr", JvmOpcodes.JSR, ShortJumpInstruction.CODEC);
    public static final Opcode<UnsignedByteInstruction> RET = make("ret", JvmOpcodes.RET, UnsignedByteInstruction.CODEC);
    public static final Opcode<TableSwitchInstruction> TABLE_SWITCH = make("tableswitch", JvmOpcodes.TABLE_SWITCH, TableSwitchInstruction.CODEC);
    public static final Opcode<LookupSwitchInstruction> LOOKUP_SWITCH = make("lookupswitch", JvmOpcodes.LOOKUP_SWITCH, LookupSwitchInstruction.CODEC);
    public static final Opcode<EmptyInstruction> IRETURN = make("ireturn", JvmOpcodes.IRETURN, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> LRETURN = make("lreturn", JvmOpcodes.LRETURN, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> FRETURN = make("freturn", JvmOpcodes.FRETURN, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> DRETURN = make("dreturn", JvmOpcodes.DRETURN, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ARETURN = make("areturn", JvmOpcodes.ARETURN, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> RETURN = make("return", JvmOpcodes.RETURN, EmptyInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> GETSTATIC = make("getstatic", JvmOpcodes.GETSTATIC, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> PUTSTATIC = make("putstatic", JvmOpcodes.PUTSTATIC, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> GETFIELD = make("getfield", JvmOpcodes.GETFIELD, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> PUTFIELD = make("putfield", JvmOpcodes.PUTFIELD, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> INVOKEVIRTUAL = make("invokevirtual", JvmOpcodes.INVOKEVIRTUAL, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> INVOKESPECIAL = make("invokespecial", JvmOpcodes.INVOKESPECIAL, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> INVOKESTATIC = make("invokestatic", JvmOpcodes.INVOKESTATIC, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> INVOKEINTERFACE = make("invokeinterface", JvmOpcodes.INVOKEINTERFACE, UnsignedShortInstruction.CODEC);
    public static final Opcode<TwoUnsignedShortInstruction> INVOKEDYNAMIC = make("invokedynamic", JvmOpcodes.INVOKEDYNAMIC, TwoUnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> NEW = make("new", JvmOpcodes.NEW, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedByteInstruction> NEWARRAY = make("newarray", JvmOpcodes.NEWARRAY, UnsignedByteInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> ANEWARRAY = make("anewarray", JvmOpcodes.ANEWARRAY, UnsignedShortInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ARRAYLENGTH = make("arraylength", JvmOpcodes.ARRAYLENGTH, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ATHROW = make("athrow", JvmOpcodes.ATHROW, EmptyInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> CHECKCAST = make("checkcast", JvmOpcodes.CHECKCAST, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> INSTANCEOF = make("instanceof", JvmOpcodes.INSTANCEOF, UnsignedShortInstruction.CODEC);
    public static final Opcode<EmptyInstruction> MONITORENTER = make("monitorenter", JvmOpcodes.MONITORENTER, EmptyInstruction.CODEC);
    public static final Opcode<EmptyInstruction> MONITOREXIT = make("monitorexit", JvmOpcodes.MONITOREXIT, EmptyInstruction.CODEC);
    public static final Opcode<WideInstruction> WIDE = make("wide", JvmOpcodes.WIDE, WideInstruction.CODEC);
    public static final Opcode<TwoUnsignedShortInstruction> MULTINEWARRAY = make("multinewarray", JvmOpcodes.MULTINEWARRAY, TwoUnsignedShortInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IFNULL = make("ifnull", JvmOpcodes.IFNULL, ShortJumpInstruction.CODEC);
    public static final Opcode<ShortJumpInstruction> IFNONNULL = make("ifnonnull", JvmOpcodes.IFNONNULL, ShortJumpInstruction.CODEC);
    public static final Opcode<IntJumpInstruction> GOTO_W = make("goto_w", JvmOpcodes.GOTO_W, IntJumpInstruction.CODEC);
    public static final Opcode<IntJumpInstruction> JSR_W = make("jsr_w", JvmOpcodes.JSR_W, IntJumpInstruction.CODEC);

    private final String name;
    private final int opcode;
    private final Codec<T> codec;

    /**
     * @param name   Opcode name.
     * @param opcode Instruction opcode.
     * @param codec  Codec.
     */
    private Opcode(String name, int opcode, Codec<T> codec) {
        this.name = name;
        this.opcode = opcode;
        this.codec = codec;
    }

    /**
     * @param n Opcode.
     * @return Opcode information or {@code null},
     * if it wasn't found.
     */
    @Nullable
    public static <T extends Instruction<T>> Opcode<T> of(int n) {
        Opcode<?>[] opcodes;
        if (n < 0 || n >= (opcodes = ALL_OPCODES).length) {
            return null;
        }
        return (Opcode<T>) opcodes[n];
    }

    /**
     * @param name Opcode name.
     * @return Opcode by it's name or {@code null},
     * if it wasn't found.
     */
    @Nullable
    public static <T extends Instruction<T>> Opcode<T> byName(String name) {
        return (Opcode<T>) BY_NAME.get(name);
    }

    /**
     * @return Opcode name.
     */
    public String name() {
        return name;
    }

    /**
     * @return Numeric opcode.
     */
    public int opcode() {
        return opcode;
    }

    /**
     * @return Codec.
     */
    public Codec<T> codec() {
        return codec;
    }

    static <T extends Instruction<T>> Opcode<T> require(int n) throws IOException {
        Opcode<T> opcode = of(n);
        if (opcode == null) {
            throw new InvalidClassException("Unknown opcode " + n);
        }
        return opcode;
    }

    private static <T extends Instruction<T>> Opcode<T> make(String name, int n, Codec<T> codec) {
        Opcode<T> opcode = new Opcode<>(name, n, codec);
        if (ALL_OPCODES[n] != null) {
            throw new AssertionError("Duplicate opcode " + n + " (" + ALL_OPCODES[n].name() + " => " + name + ')');
        }
        ALL_OPCODES[n] = opcode;
        if (BY_NAME.putIfAbsent(name, opcode) != null) {
            throw new AssertionError("Duplicate opcode " + name);
        }
        return opcode;
    }
}
