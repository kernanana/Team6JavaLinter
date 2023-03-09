package ASMPracticeCode.NamingConventionDummyData;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.SimpleRemapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;


import java.lang.Object;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchFieldException {
        ClassNode classNode = new ClassNode();
        org.objectweb.asm.ClassReader reader = new org.objectweb.asm.ClassReader("ASMPracticeCode.NamingConventionDummyData.EverythingErrorClass");
        reader.accept(classNode, ClassReader.EXPAND_FRAMES);
//        FieldNode fn = new FieldNode(Opcodes.ACC_PUBLIC, "Field", Type.getDescriptor(int.class),null,null);
//        classNode.fields.add(fn);
//        classNode.fields.remove("Fieldsss");

        CustomClassWriter writer = new CustomClassWriter();
        byte[] output = writer.addField();
        MyClassLoader mcl = new MyClassLoader();
        Class c = mcl.defineClass("ASMPracticeCode.NamingConventionDummyData.EverythingErrorClass", output);
        System.out.println(c.getField("Fieldsss"));
//        System.out.println(c.getFields().length);
//        System.out.println(c.getField("Field"));
//        System.out.println(c.getField("FIELD_3"));

    }

}


