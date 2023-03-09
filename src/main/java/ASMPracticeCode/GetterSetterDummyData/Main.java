package ASMPracticeCode.GetterSetterDummyData;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String args[]){
        try {
            org.objectweb.asm.ClassReader reader = new org.objectweb.asm.ClassReader("ASMPracticeCode.GetterSetterDummyData.GetterSetterClass");
            // 2. ClassNode is just a data container for the parsed class
            ClassNode classNode = new ClassNode();

            System.out.println("ALOAD: " + Opcodes.ALOAD);
            System.out.println("ILOAD: " + Opcodes.ILOAD);
            System.out.println("IRETURN: " + Opcodes.IRETURN);
            System.out.println("ARETURN: " + Opcodes.ARETURN);
            System.out.println("PUTFIELD: " + Opcodes.PUTFIELD);
            System.out.println("GETFIELD: " + Opcodes.GETFIELD);
            System.out.println("ACC_PUBLIC: " + Opcodes.ACC_PUBLIC);

            // 3. Tell the Reader to parse the specified class and store its data in our ClassNode.
            // EXPAND_FRAMES means: I want my code to work. (Always pass this flag.)
            reader.accept(classNode, ClassReader.EXPAND_FRAMES);

            checkForSetters(classNode);
            // Now we can navigate the classNode and look for things we are interested in.
            checkForGetters(classNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkForSetters(ClassNode classNode) {
        List<MethodNode> methods = (List<MethodNode>) classNode.methods;
        System.out.println("start ins");
        for(int i=1; i<methods.size();i++){
            InsnList inst = methods.get(i).instructions;
            System.out.println(i+"---------");
//            for (int j=0; j<inst.size();j++){
//                System.out.println(inst.get(j).getOpcode());
//            }
            if(Opcodes.ALOAD == inst.get(2).getOpcode() || Opcodes.ILOAD == inst.get(2).getOpcode()){
                if(Opcodes.ILOAD == inst.get(3).getOpcode() || Opcodes.ALOAD == inst.get(3).getOpcode()){
                    if(Opcodes.PUTFIELD == inst.get(4).getOpcode()) {
                        System.out.println(methods.get(i).name + ": is a setter");
                    }
                    else{
                        System.out.println(methods.get(i).name + ": is NOT a setter");
                    }
                }
                else{
                    System.out.println(methods.get(i).name + ": is NOT a setter");
                }
            }
            else{
                System.out.println(methods.get(i).name + ": is NOT a setter");
            }
        }
    }

    private static void checkForGetters(ClassNode classNode) {
        List<MethodNode> methods = (List<MethodNode>) classNode.methods;
        System.out.println("start ins");
        for(int i=1; i<methods.size();i++){
            InsnList inst = methods.get(i).instructions;
            System.out.println(i+"---------");
            if(Opcodes.ALOAD == inst.get(2).getOpcode()){
                if(Opcodes.GETFIELD == inst.get(3).getOpcode()){
                    if(inst.get(4).getOpcode() == Opcodes.ARETURN || inst.get(4).getOpcode() == Opcodes.IRETURN || inst.get(4).getOpcode() == Opcodes.RETURN || inst.get(4).getOpcode() == Opcodes.FRETURN || inst.get(4).getOpcode() == Opcodes.DRETURN || inst.get(4).getOpcode() == Opcodes.LRETURN){
                        System.out.println(methods.get(i).name + ": is a getter");
                    }
                }
            }
            else{
                System.out.println(methods.get(i).name + ": is NOT a getter");
            }
        }
    }
    private static boolean singletonCheck(ClassNode cn){
        List<FieldNode> fn = cn.fields;
        String className = cn.name;
        List<MethodNode> methods = (List<MethodNode>) cn.methods;
        for(int i=0;i<methods.size();i++){
            boolean returnsSingleTon = Type.getReturnType(methods.get(i).desc).equals(className);
            if(returnsSingleTon){
                return true;
            }
        }
        return false;

    }

}
