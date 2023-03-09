package ASMPracticeCode.NamingConventionDummyData;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

public class CustomClassWriter {
    AddFieldAdapter addFieldAdapter;
    static String className = "ASMPracticeCode.NamingConventionDummyData.EverythingErrorClass";
    static String cloneableInterface = "java/lang/Cloneable";
    ClassReader reader;
    ClassWriter writer;

    PublicizeMethodAdapter pubMethAdapter;

    public CustomClassWriter() throws IOException {
        reader = new ClassReader(className);
        writer = new ClassWriter(reader, 0);
    }

    public byte[] addField() {
        addFieldAdapter = new AddFieldAdapter(
                "Fieldsss",
                Opcodes.ACC_PUBLIC,
                writer);
        reader.accept(addFieldAdapter, 0);
        System.out.println("worked");
        return writer.toByteArray();
    }


    public byte[] publicizeMethod() {
        pubMethAdapter = new PublicizeMethodAdapter(writer);
        reader.accept(pubMethAdapter, 0);
        return writer.toByteArray();
    }
}
