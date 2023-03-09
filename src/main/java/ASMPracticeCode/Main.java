package ASMPracticeCode;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String args[]){
        try {
            ClassReader reader = new ClassReader("ASMPracticeCode.HollywoodPrincipleDummyData.LowLevelComponent");
            // 2. ClassNode is just a data container for the parsed class
            ClassNode classNode = new ClassNode();

            // 3. Tell the Reader to parse the specified class and store its data in our ClassNode.
            // EXPAND_FRAMES means: I want my code to work. (Always pass this flag.)
            reader.accept(classNode, ClassReader.EXPAND_FRAMES);

            // Now we can navigate the classNode and look for things we are interested in.
            printClass(classNode);

            printFields(classNode);

            printMethods(classNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean styleCheckEqualsHashCode(ClassNode classNode) {
        List<MethodNode> methods = (List<MethodNode>) classNode.methods;
        boolean equalsFound = false;
        boolean hashcodeFound = false;
        for (MethodNode method : methods) {
            if (method.name.equals("equals")){
                equalsFound = true;
            }
            if (method.name.equals("hashCode")){
                hashcodeFound = true;
            }
            System.out.println("	Method: " + method.name);
        }
        if (equalsFound ^ hashcodeFound){
            return false;
        }
        return true;
    }

//    private static boolean decorator(ClassNode classNode) {
//        // Print all fields (note the cast; ASM doesn't store generic data with its Lists)
//        List<FieldNode> fields = (List<FieldNode>) classNode.fields;
//        String extendedClass = classNode.superName;
//        for (FieldNode field : fields) {
//            if ()
//            System.out.println("	Field: " + field.name);
//        }
//        return false;
//    }

    private static void printClass(ClassNode classNode) {
        System.out.println("Class's Internal JVM name: " + classNode.name);
        System.out.println("User-friendly name: "
                + Type.getObjectType(classNode.name).getClassName());
        System.out.println("public? "
                + ((classNode.access & Opcodes.ACC_PUBLIC) != 0));
        System.out.println("Extends: " + classNode.superName);
        System.out.println("Implements: " + classNode.interfaces);
        // TODO: how do I write a lint check to tell if this class has a bad name?
    }

    private static void printFields(ClassNode classNode) {
        // Print all fields (note the cast; ASM doesn't store generic data with its Lists)
        List<FieldNode> fields = (List<FieldNode>) classNode.fields;
        for (FieldNode field : fields) {
            System.out.println("	Field: " + field.name);
            System.out.println("	Internal JVM type: " + field.desc);
            System.out.println("	User-friendly type: "
                    + Type.getObjectType(field.desc).getClassName());
            // Query the access modifiers with the ACC_* constants.

            System.out.println("	public? "
                    + ((field.access & Opcodes.ACC_PUBLIC) != 0));
            // TODO: how do you tell if something has package-private access? (ie no access modifiers?)

            // TODO: how do I write a lint check to tell if this field has a bad name?

            System.out.println();
        }
    }

    private static void printMethods(ClassNode classNode) {
        List<MethodNode> methods = (List<MethodNode>) classNode.methods;
        for (MethodNode method : methods) {
            System.out.println("	Method: " + method.name);
            System.out
                    .println("	Internal JVM method signature: " + method.desc);

            System.out.println("	Return type: "
                    + Type.getReturnType(method.desc).getClassName());

            System.out.println("	Args: ");
            for (Type argType : Type.getArgumentTypes(method.desc)) {
                System.out.println("		" + argType.getClassName());
                // FIXME: what is the argument's *variable* name?
            }

            System.out.println("	public? "
                    + ((method.access & Opcodes.ACC_PUBLIC) != 0));
            System.out.println("	static? "
                    + ((method.access & Opcodes.ACC_STATIC) != 0));
            // How do you tell if something has default access? (ie no access modifiers?)

            System.out.println();

            // Print the method's instructions
            printInstructions(method);
        }
    }

    private static void printInstructions(MethodNode methodNode) {
        System.out.println("PRINTING INSTR");
        InsnList instructions = methodNode.instructions;
        for (int i = 0; i < instructions.size(); i++) {

            // We don't know immediately what kind of instruction we have.
            AbstractInsnNode insn = instructions.get(i);

            // FIXME: Is instanceof the best way to deal with the instruction's type?
            if (insn instanceof MethodInsnNode) {
                // A method call of some sort; what other useful fields does this object have?
                MethodInsnNode methodCall = (MethodInsnNode) insn;
                System.out.println("		Call method: " + methodCall.owner + " "
                        + methodCall.name);
            } else if (insn instanceof VarInsnNode) {
                // Some some kind of variable *LOAD or *STORE operation.
                VarInsnNode varInsn = (VarInsnNode) insn;
                int opCode = varInsn.getOpcode();
                // See VarInsnNode.setOpcode for the list of possible values of
                // opCode. These are from a variable-related subset of Java
                // opcodes.
            }
            // There are others...
            // This list of direct known subclasses may be useful:
            // http://asm.ow2.org/asm50/javadoc/user/org/objectweb/asm/tree/AbstractInsnNode.html

            // TODO: how do I write a lint check to tell if this method has a bad name?
        }
    }
}
