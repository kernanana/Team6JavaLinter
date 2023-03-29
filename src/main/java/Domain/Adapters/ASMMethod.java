package Domain.Adapters;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.List;


public class ASMMethod implements MethodAdapter{
    private final MethodNode node;
    public ASMMethod (MethodNode node) {
        this.node = node;
    }

    @Override
    public String getMethodName() {
        return node.name;
    }

    @Override
    public boolean getIsPublic() {
        return (node.access & Opcodes.ACC_PUBLIC) != 0;
    }

    @Override
    public String getReturnType() {
        return Type.getReturnType(node.desc).getClassName();
    }

    @Override
    public List<String> getArgTypes() {
        ArrayList<String> argTypes = new ArrayList<>();
        for (Type argType : Type.getArgumentTypes(node.desc)) {
            argTypes.add(argType.getClassName());
        }
        return argTypes;
    }

    @Override
    public boolean isStatic() {
        return (node.access & Opcodes.ACC_STATIC) != 0;
    }


    @Override
    public List<String> getInstructions() {
        InsnList instructions = node.instructions;
        ArrayList<String> instructionStrings = new ArrayList<>();
        for (int i = 0; i < instructions.size(); i++) {
            AbstractInsnNode insn = instructions.get(i);
            if (insn instanceof MethodInsnNode) {
                MethodInsnNode methodCall = (MethodInsnNode) insn;
                String str = methodCall.owner + " "  + methodCall.name;
                instructionStrings.add(str);
            }
        }
        return instructionStrings;
    }

    @Override
    public List<Integer> getInstOpCodes() {
        List<Integer> opCodes = new ArrayList<>();
        InsnList instructions = node.instructions;
        for(int i=0;i<instructions.size();i++){
            opCodes.add(i,instructions.get(i).getOpcode());
        }
        return opCodes;
    }

    @Override
    public boolean isSetter() {
        List<Integer> op = getInstOpCodes();
        if(op.get(2) == Opcodes.ALOAD || op.get(2) == Opcodes.ILOAD){
            if (op.get(3) == Opcodes.ALOAD || op.get(3) == Opcodes.ILOAD){
                if (op.get(4) == Opcodes.PUTFIELD){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean getIsAbstract() {
        return (node.access & Opcodes.ACC_ABSTRACT) != 0;
    }

    @Override
    public boolean getIsPrivate() {
        return (node.access & Opcodes.ACC_PRIVATE) != 0;
    }

    @Override
    public boolean isGetter() {
        List<Integer> op = getInstOpCodes();
        if(op.get(2) == Opcodes.ALOAD){
            if(op.get(3) == Opcodes.GETFIELD){
                if(op.get(4) == Opcodes.ARETURN || op.get(4) == Opcodes.IRETURN || op.get(4) == Opcodes.RETURN || op.get(4) == Opcodes.FRETURN || op.get(4) == Opcodes.DRETURN || op.get(4) == Opcodes.LRETURN){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getSignature() {
        StringBuilder sb = new StringBuilder();

        sb.append(getReturnType()).append(" ");
        sb.append(getMethodName()).append("(");

        if(getArgTypes().size() == 0)
            return sb.append(")").toString();

        for(String type : getArgTypes()) {
            sb.append(type).append(", ");
        }

        return sb.replace(sb.length() - 2, sb.length(), ")").toString();
    }

}
