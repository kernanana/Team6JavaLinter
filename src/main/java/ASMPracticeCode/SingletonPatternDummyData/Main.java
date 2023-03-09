package ASMPracticeCode.SingletonPatternDummyData;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String args[]) {
        try {
            org.objectweb.asm.ClassReader reader = new org.objectweb.asm.ClassReader("ASMPracticeCode.SingletonPatternDummyData.EagerSingleton");
            // 2. ClassNode is just a data container for the parsed class
            ClassNode classNode = new ClassNode();

//            System.out.println("ALOAD: " + Opcodes.ALOAD);
//            System.out.println("ILOAD: " + Opcodes.ILOAD);
//            System.out.println("IRETURN: " + Opcodes.IRETURN);
//            System.out.println("ARETURN: " + Opcodes.ARETURN);
//            System.out.println("PUTFIELD: " + Opcodes.PUTFIELD);
//            System.out.println("GETFIELD: " + Opcodes.GETFIELD);
//            System.out.println("ACC_PUBLIC: " + Opcodes.ACC_PUBLIC);

            // 3. Tell the Reader to parse the specified class and store its data in our ClassNode.
            // EXPAND_FRAMES means: I want my code to work. (Always pass this flag.)
            reader.accept(classNode, ClassReader.EXPAND_FRAMES);

            eagerSingletonCheck(classNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void eagerSingletonCheck(ClassNode classNode) {
        boolean hasprivateStaticInstanceField = false;
        boolean hasInstanceReturnMethod = false;
        String className = classNode.name;
        className = className.substring(className.lastIndexOf("/")+1);
        System.out.println("className: " + className);
        List<FieldNode> fn = classNode.fields;
        List<MethodNode> mn = classNode.methods;
        for(int i = 0; i < fn.size();i++){
            String name = fn.get(i).desc;
            String fieldDataType =  name.substring(name.lastIndexOf("/")+1, name.length()-1);
            System.out.println("Field1: "+fieldDataType);
            if((className.equals(fieldDataType) && true) && (((fn.get(i).access) & (Opcodes.ACC_STATIC)) != 0) && (((fn.get(i).access) & (Opcodes.ACC_PRIVATE)) != 0)){
                System.out.println("Access: " + fn.get(i).access);
                System.out.println("Private: "+ Opcodes.ACC_PRIVATE);
                System.out.println("Static: "+Opcodes.ACC_STATIC);
                hasprivateStaticInstanceField = true;
            }
        }

        if(hasprivateStaticInstanceField){
            for(int i=0;i<mn.size();i++){
                Type returnType = Type.getReturnType(mn.get(i).desc);
                System.out.println("Return: " + returnType);
                if (returnType.getReturnType() == returnType){

                }
            }
        }
        System.out.println("This is not Singleton");

    }
}
