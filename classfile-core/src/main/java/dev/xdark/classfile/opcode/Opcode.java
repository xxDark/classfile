package dev.xdark.classfile.opcode;

import dev.xdark.classfile.InvalidClassException;
import dev.xdark.classfile.io.Codec;
import dev.xdark.classfile.io.Input;
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
    public static final Opcode<EmptyInstruction> NOP = empty("nop", JvmOpcodes.NOP);
    public static final Opcode<EmptyInstruction> ACONST_NULL = empty("aconst_null", JvmOpcodes.ACONST_NULL);
    public static final Opcode<EmptyInstruction> ICONST_M1 = empty("iconst_m1", JvmOpcodes.ICONST_M1);
    public static final Opcode<EmptyInstruction> ICONST_0 = empty("iconst_0", JvmOpcodes.ICONST_0);
    public static final Opcode<EmptyInstruction> ICONST_1 = empty("iconst_1", JvmOpcodes.ICONST_1);
    public static final Opcode<EmptyInstruction> ICONST_2 = empty("iconst_2", JvmOpcodes.ICONST_2);
    public static final Opcode<EmptyInstruction> ICONST_3 = empty("iconst_3", JvmOpcodes.ICONST_3);
    public static final Opcode<EmptyInstruction> ICONST_4 = empty("iconst_4", JvmOpcodes.ICONST_4);
    public static final Opcode<EmptyInstruction> ICONST_5 = empty("iconst_5", JvmOpcodes.ICONST_5);
    public static final Opcode<EmptyInstruction> LCONST_0 = empty("lconst_0", JvmOpcodes.LCONST_0);
    public static final Opcode<EmptyInstruction> LCONST_1 = empty("lconst_1", JvmOpcodes.LCONST_1);
    public static final Opcode<EmptyInstruction> FCONST_0 = empty("fconst_0", JvmOpcodes.FCONST_0);
    public static final Opcode<EmptyInstruction> FCONST_1 = empty("fconst_1", JvmOpcodes.FCONST_1);
    public static final Opcode<EmptyInstruction> FCONST_2 = empty("fconst_2", JvmOpcodes.FCONST_2);
    public static final Opcode<EmptyInstruction> DCONST_0 = empty("dconst_0", JvmOpcodes.DCONST_0);
    public static final Opcode<EmptyInstruction> DCONST_1 = empty("dconst_1", JvmOpcodes.DCONST_1);
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
    public static final Opcode<EmptyInstruction> ILOAD_0 = empty("iload_0", JvmOpcodes.ILOAD_0);
    public static final Opcode<EmptyInstruction> ILOAD_1 = empty("iload_1", JvmOpcodes.ILOAD_1);
    public static final Opcode<EmptyInstruction> ILOAD_2 = empty("iload_2", JvmOpcodes.ILOAD_2);
    public static final Opcode<EmptyInstruction> ILOAD_3 = empty("iload_3", JvmOpcodes.ILOAD_3);
    public static final Opcode<EmptyInstruction> LLOAD_0 = empty("lload_0", JvmOpcodes.LLOAD_0);
    public static final Opcode<EmptyInstruction> LLOAD_1 = empty("lload_1", JvmOpcodes.LLOAD_1);
    public static final Opcode<EmptyInstruction> LLOAD_2 = empty("lload_2", JvmOpcodes.LLOAD_2);
    public static final Opcode<EmptyInstruction> LLOAD_3 = empty("lload_3", JvmOpcodes.LLOAD_3);
    public static final Opcode<EmptyInstruction> FLOAD_0 = empty("fload_0", JvmOpcodes.FLOAD_0);
    public static final Opcode<EmptyInstruction> FLOAD_1 = empty("fload_1", JvmOpcodes.FLOAD_1);
    public static final Opcode<EmptyInstruction> FLOAD_2 = empty("fload_2", JvmOpcodes.FLOAD_2);
    public static final Opcode<EmptyInstruction> FLOAD_3 = empty("fload_3", JvmOpcodes.FLOAD_3);
    public static final Opcode<EmptyInstruction> DLOAD_0 = empty("dload_0", JvmOpcodes.DLOAD_0);
    public static final Opcode<EmptyInstruction> DLOAD_1 = empty("dload_1", JvmOpcodes.DLOAD_1);
    public static final Opcode<EmptyInstruction> DLOAD_2 = empty("dload_2", JvmOpcodes.DLOAD_2);
    public static final Opcode<EmptyInstruction> DLOAD_3 = empty("dload_3", JvmOpcodes.DLOAD_3);
    public static final Opcode<EmptyInstruction> ALOAD_0 = empty("aload_0", JvmOpcodes.ALOAD_0);
    public static final Opcode<EmptyInstruction> ALOAD_1 = empty("aload_1", JvmOpcodes.ALOAD_1);
    public static final Opcode<EmptyInstruction> ALOAD_2 = empty("aload_2", JvmOpcodes.ALOAD_2);
    public static final Opcode<EmptyInstruction> ALOAD_3 = empty("aload_3", JvmOpcodes.ALOAD_3);
    public static final Opcode<EmptyInstruction> IALOAD = empty("iaload", JvmOpcodes.IALOAD);
    public static final Opcode<EmptyInstruction> LALOAD = empty("laload", JvmOpcodes.LALOAD);
    public static final Opcode<EmptyInstruction> FALOAD = empty("faload", JvmOpcodes.FALOAD);
    public static final Opcode<EmptyInstruction> DALOAD = empty("daload", JvmOpcodes.DALOAD);
    public static final Opcode<EmptyInstruction> AALOAD = empty("aaload", JvmOpcodes.AALOAD);
    public static final Opcode<EmptyInstruction> BALOAD = empty("baload", JvmOpcodes.BALOAD);
    public static final Opcode<EmptyInstruction> CALOAD = empty("caload", JvmOpcodes.CALOAD);
    public static final Opcode<EmptyInstruction> SALOAD = empty("saload", JvmOpcodes.SALOAD);
    public static final Opcode<EmptyInstruction> ISTORE = empty("istore", JvmOpcodes.ISTORE);
    public static final Opcode<EmptyInstruction> LSTORE = empty("lstore", JvmOpcodes.LSTORE);
    public static final Opcode<EmptyInstruction> FSTORE = empty("fstore", JvmOpcodes.FSTORE);
    public static final Opcode<EmptyInstruction> DSTORE = empty("dstore", JvmOpcodes.DSTORE);
    public static final Opcode<EmptyInstruction> ASTORE = empty("astore", JvmOpcodes.ASTORE);
    public static final Opcode<EmptyInstruction> ISTORE_0 = empty("istore_0", JvmOpcodes.ISTORE_0);
    public static final Opcode<EmptyInstruction> ISTORE_1 = empty("istore_1", JvmOpcodes.ISTORE_1);
    public static final Opcode<EmptyInstruction> ISTORE_2 = empty("istore_2", JvmOpcodes.ISTORE_2);
    public static final Opcode<EmptyInstruction> ISTORE_3 = empty("istore_3", JvmOpcodes.ISTORE_3);
    public static final Opcode<EmptyInstruction> LSTORE_0 = empty("lstore_0", JvmOpcodes.LSTORE_0);
    public static final Opcode<EmptyInstruction> LSTORE_1 = empty("lstore_1", JvmOpcodes.LSTORE_1);
    public static final Opcode<EmptyInstruction> LSTORE_2 = empty("lstore_2", JvmOpcodes.LSTORE_2);
    public static final Opcode<EmptyInstruction> LSTORE_3 = empty("lstore_3", JvmOpcodes.LSTORE_3);
    public static final Opcode<EmptyInstruction> FSTORE_0 = empty("fstore_0", JvmOpcodes.FSTORE_0);
    public static final Opcode<EmptyInstruction> FSTORE_1 = empty("fstore_1", JvmOpcodes.FSTORE_1);
    public static final Opcode<EmptyInstruction> FSTORE_2 = empty("fstore_2", JvmOpcodes.FSTORE_2);
    public static final Opcode<EmptyInstruction> FSTORE_3 = empty("fstore_3", JvmOpcodes.FSTORE_3);
    public static final Opcode<EmptyInstruction> DSTORE_0 = empty("dstore_0", JvmOpcodes.DSTORE_0);
    public static final Opcode<EmptyInstruction> DSTORE_1 = empty("dstore_1", JvmOpcodes.DSTORE_1);
    public static final Opcode<EmptyInstruction> DSTORE_2 = empty("dstore_2", JvmOpcodes.DSTORE_2);
    public static final Opcode<EmptyInstruction> DSTORE_3 = empty("dstore_3", JvmOpcodes.DSTORE_3);
    public static final Opcode<EmptyInstruction> ASTORE_0 = empty("astore_0", JvmOpcodes.ASTORE_0);
    public static final Opcode<EmptyInstruction> ASTORE_1 = empty("astore_1", JvmOpcodes.ASTORE_1);
    public static final Opcode<EmptyInstruction> ASTORE_2 = empty("astore_2", JvmOpcodes.ASTORE_2);
    public static final Opcode<EmptyInstruction> ASTORE_3 = empty("astore_3", JvmOpcodes.ASTORE_3);
    public static final Opcode<EmptyInstruction> IASTORE = empty("iastore", JvmOpcodes.IASTORE);
    public static final Opcode<EmptyInstruction> LASTORE = empty("lastore", JvmOpcodes.LASTORE);
    public static final Opcode<EmptyInstruction> FASTORE = empty("fastore", JvmOpcodes.FASTORE);
    public static final Opcode<EmptyInstruction> DASTORE = empty("dastore", JvmOpcodes.DASTORE);
    public static final Opcode<EmptyInstruction> AASTORE = empty("aastore", JvmOpcodes.AASTORE);
    public static final Opcode<EmptyInstruction> BASTORE = empty("bastore", JvmOpcodes.BASTORE);
    public static final Opcode<EmptyInstruction> CASTORE = empty("castore", JvmOpcodes.CASTORE);
    public static final Opcode<EmptyInstruction> SASTORE = empty("sastore", JvmOpcodes.SASTORE);
    public static final Opcode<EmptyInstruction> POP = empty("pop", JvmOpcodes.POP);
    public static final Opcode<EmptyInstruction> POP2 = empty("pop2", JvmOpcodes.POP2);
    public static final Opcode<EmptyInstruction> DUP = empty("dup", JvmOpcodes.DUP);
    public static final Opcode<EmptyInstruction> DUP_X1 = empty("dup_x1", JvmOpcodes.DUP_X1);
    public static final Opcode<EmptyInstruction> DUP_X2 = empty("dup_x2", JvmOpcodes.DUP_X2);
    public static final Opcode<EmptyInstruction> DUP2 = empty("dup2", JvmOpcodes.DUP2);
    public static final Opcode<EmptyInstruction> DUP2_X1 = empty("dup2_x1", JvmOpcodes.DUP2_X1);
    public static final Opcode<EmptyInstruction> DUP2_X2 = empty("dup2_x2", JvmOpcodes.DUP2_X2);
    public static final Opcode<EmptyInstruction> SWAP = empty("swap", JvmOpcodes.SWAP);
    public static final Opcode<EmptyInstruction> IADD = empty("iadd", JvmOpcodes.IADD);
    public static final Opcode<EmptyInstruction> LADD = empty("ladd", JvmOpcodes.LADD);
    public static final Opcode<EmptyInstruction> FADD = empty("fadd", JvmOpcodes.FADD);
    public static final Opcode<EmptyInstruction> DADD = empty("dadd", JvmOpcodes.DADD);
    public static final Opcode<EmptyInstruction> ISUB = empty("isub", JvmOpcodes.ISUB);
    public static final Opcode<EmptyInstruction> LSUB = empty("lsub", JvmOpcodes.LSUB);
    public static final Opcode<EmptyInstruction> FSUB = empty("fsub", JvmOpcodes.FSUB);
    public static final Opcode<EmptyInstruction> DSUB = empty("dsub", JvmOpcodes.DSUB);
    public static final Opcode<EmptyInstruction> IMUL = empty("imul", JvmOpcodes.IMUL);
    public static final Opcode<EmptyInstruction> LMUL = empty("lmul", JvmOpcodes.LMUL);
    public static final Opcode<EmptyInstruction> FMUL = empty("fmul", JvmOpcodes.FMUL);
    public static final Opcode<EmptyInstruction> DMUL = empty("dmul", JvmOpcodes.DMUL);
    public static final Opcode<EmptyInstruction> IDIV = empty("idiv", JvmOpcodes.IDIV);
    public static final Opcode<EmptyInstruction> LDIV = empty("ldiv", JvmOpcodes.LDIV);
    public static final Opcode<EmptyInstruction> FDIV = empty("fdiv", JvmOpcodes.FDIV);
    public static final Opcode<EmptyInstruction> DDIV = empty("ddiv", JvmOpcodes.DDIV);
    public static final Opcode<EmptyInstruction> IREM = empty("irem", JvmOpcodes.IREM);
    public static final Opcode<EmptyInstruction> LREM = empty("lrem", JvmOpcodes.LREM);
    public static final Opcode<EmptyInstruction> FREM = empty("frem", JvmOpcodes.FREM);
    public static final Opcode<EmptyInstruction> DREM = empty("drem", JvmOpcodes.DREM);
    public static final Opcode<EmptyInstruction> INEG = empty("ineg", JvmOpcodes.INEG);
    public static final Opcode<EmptyInstruction> LNEG = empty("lneg", JvmOpcodes.LNEG);
    public static final Opcode<EmptyInstruction> FNEG = empty("fneg", JvmOpcodes.FNEG);
    public static final Opcode<EmptyInstruction> DNEG = empty("dneg", JvmOpcodes.DNEG);
    public static final Opcode<EmptyInstruction> ISHL = empty("ishl", JvmOpcodes.ISHL);
    public static final Opcode<EmptyInstruction> LSHL = empty("lshl", JvmOpcodes.LSHL);
    public static final Opcode<EmptyInstruction> ISHR = empty("ishr", JvmOpcodes.ISHR);
    public static final Opcode<EmptyInstruction> LSHR = empty("lshr", JvmOpcodes.LSHR);
    public static final Opcode<EmptyInstruction> IUSHR = empty("iushr", JvmOpcodes.IUSHR);
    public static final Opcode<EmptyInstruction> LUSHR = empty("lushr", JvmOpcodes.LUSHR);
    public static final Opcode<EmptyInstruction> IAND = empty("iand", JvmOpcodes.IAND);
    public static final Opcode<EmptyInstruction> LAND = empty("land", JvmOpcodes.LAND);
    public static final Opcode<EmptyInstruction> IOR = empty("ior", JvmOpcodes.IOR);
    public static final Opcode<EmptyInstruction> LOR = empty("lor", JvmOpcodes.LOR);
    public static final Opcode<EmptyInstruction> IXOR = empty("ixor", JvmOpcodes.IXOR);
    public static final Opcode<EmptyInstruction> LXOR = empty("lxor", JvmOpcodes.LXOR);
    public static final Opcode<IincInstruction> IINC = make("iinc", JvmOpcodes.IINC, IincInstruction.CODEC);
    public static final Opcode<EmptyInstruction> I2L = empty("i2l", JvmOpcodes.I2L);
    public static final Opcode<EmptyInstruction> I2F = empty("i2f", JvmOpcodes.I2F);
    public static final Opcode<EmptyInstruction> I2D = empty("i2d", JvmOpcodes.I2D);
    public static final Opcode<EmptyInstruction> L2I = empty("l2i", JvmOpcodes.L2I);
    public static final Opcode<EmptyInstruction> L2F = empty("l2f", JvmOpcodes.L2F);
    public static final Opcode<EmptyInstruction> L2D = empty("l2d", JvmOpcodes.L2D);
    public static final Opcode<EmptyInstruction> F2I = empty("f2i", JvmOpcodes.F2I);
    public static final Opcode<EmptyInstruction> F2L = empty("f2l", JvmOpcodes.F2L);
    public static final Opcode<EmptyInstruction> F2D = empty("f2d", JvmOpcodes.F2D);
    public static final Opcode<EmptyInstruction> D2I = empty("d2i", JvmOpcodes.D2I);
    public static final Opcode<EmptyInstruction> D2L = empty("d2l", JvmOpcodes.D2L);
    public static final Opcode<EmptyInstruction> D2F = empty("d2f", JvmOpcodes.D2F);
    public static final Opcode<EmptyInstruction> I2B = empty("i2b", JvmOpcodes.I2B);
    public static final Opcode<EmptyInstruction> I2C = empty("i2c", JvmOpcodes.I2C);
    public static final Opcode<EmptyInstruction> I2S = empty("i2s", JvmOpcodes.I2S);
    public static final Opcode<EmptyInstruction> LCMP = empty("lcmp", JvmOpcodes.LCMP);
    public static final Opcode<EmptyInstruction> FCMPL = empty("fcmpl", JvmOpcodes.FCMPL);
    public static final Opcode<EmptyInstruction> FCMPG = empty("fcmpg", JvmOpcodes.FCMPG);
    public static final Opcode<EmptyInstruction> DCMPL = empty("dcmpl", JvmOpcodes.DCMPL);
    public static final Opcode<EmptyInstruction> DCMPG = empty("dcmpg", JvmOpcodes.DCMPG);
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
    public static final Opcode<EmptyInstruction> IRETURN = empty("ireturn", JvmOpcodes.IRETURN);
    public static final Opcode<EmptyInstruction> LRETURN = empty("lreturn", JvmOpcodes.LRETURN);
    public static final Opcode<EmptyInstruction> FRETURN = empty("freturn", JvmOpcodes.FRETURN);
    public static final Opcode<EmptyInstruction> DRETURN = empty("dreturn", JvmOpcodes.DRETURN);
    public static final Opcode<EmptyInstruction> ARETURN = empty("areturn", JvmOpcodes.ARETURN);
    public static final Opcode<EmptyInstruction> RETURN = empty("return", JvmOpcodes.RETURN);
    public static final Opcode<FieldInstruction> GETSTATIC = make("getstatic", JvmOpcodes.GETSTATIC, FieldInstruction.CODEC);
    public static final Opcode<FieldInstruction> PUTSTATIC = make("putstatic", JvmOpcodes.PUTSTATIC, FieldInstruction.CODEC);
    public static final Opcode<FieldInstruction> GETFIELD = make("getfield", JvmOpcodes.GETFIELD, FieldInstruction.CODEC);
    public static final Opcode<FieldInstruction> PUTFIELD = make("putfield", JvmOpcodes.PUTFIELD, FieldInstruction.CODEC);
    public static final Opcode<MethodInstruction> INVOKEVIRTUAL = make("invokevirtual", JvmOpcodes.INVOKEVIRTUAL, MethodInstruction.CODEC);
    public static final Opcode<MethodInstruction> INVOKESPECIAL = make("invokespecial", JvmOpcodes.INVOKESPECIAL, MethodInstruction.CODEC);
    public static final Opcode<MethodInstruction> INVOKESTATIC = make("invokestatic", JvmOpcodes.INVOKESTATIC, MethodInstruction.CODEC);
    public static final Opcode<MethodInstruction> INVOKEINTERFACE = make("invokeinterface", JvmOpcodes.INVOKEINTERFACE, MethodInstruction.CODEC);
    public static final Opcode<TwoUnsignedShortInstruction> INVOKEDYNAMIC = make("invokedynamic", JvmOpcodes.INVOKEDYNAMIC, TwoUnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> NEW = make("new", JvmOpcodes.NEW, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedByteInstruction> NEWARRAY = make("newarray", JvmOpcodes.NEWARRAY, UnsignedByteInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> ANEWARRAY = make("anewarray", JvmOpcodes.ANEWARRAY, UnsignedShortInstruction.CODEC);
    public static final Opcode<EmptyInstruction> ARRAYLENGTH = empty("arraylength", JvmOpcodes.ARRAYLENGTH);
    public static final Opcode<EmptyInstruction> ATHROW = empty("athrow", JvmOpcodes.ATHROW);
    public static final Opcode<UnsignedShortInstruction> CHECKCAST = make("checkcast", JvmOpcodes.CHECKCAST, UnsignedShortInstruction.CODEC);
    public static final Opcode<UnsignedShortInstruction> INSTANCEOF = make("instanceof", JvmOpcodes.INSTANCEOF, UnsignedShortInstruction.CODEC);
    public static final Opcode<EmptyInstruction> MONITORENTER = empty("monitorenter", JvmOpcodes.MONITORENTER);
    public static final Opcode<EmptyInstruction> MONITOREXIT = empty("monitorexit", JvmOpcodes.MONITOREXIT);
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
    public static Opcode<?> of(int n) {
        Opcode<?>[] opcodes;
        if (n < 0 || n >= (opcodes = ALL_OPCODES).length) {
            return null;
        }
        return opcodes[n];
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

    static <T extends Instruction<T>> Opcode<T> require(Input input) throws IOException {
        int n = input.readUnsignedByte();
        Opcode<T> opcode = (Opcode<T>) of(n);
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

    private static Opcode<EmptyInstruction> empty(String name, int n) {
        return make(name, n, EmptyInstruction.CODEC);
    }
}
