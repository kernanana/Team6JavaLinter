package DataSource;

import ASMPracticeCode.NamingConventionDummyData.PublicizeMethodAdapter;
import Domain.Adapters.ASMFieldVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;

public class ASMClassWriterFacade{
    private Domain.Adapters.ASMFieldVisitor ASMFieldVisitor;
    private String className;
    private ClassReader reader;
    private ClassWriter writer;

    public ASMClassWriterFacade(String cn) throws IOException {
        this.className = cn;
        reader = new ClassReader(cn);
        writer = new ClassWriter(reader, 0);
    }

    public byte[] addField(String fieldName, int accessOpcode) {
         ASMFieldVisitor = new ASMFieldVisitor(
                fieldName,
                accessOpcode,
                writer);
        reader.accept(ASMFieldVisitor, 0);
        System.out.println("worked");
        return writer.toByteArray();
    }


    public byte[] publicizeMethod() {
        PublicizeMethodAdapter pubMethAdapter = new PublicizeMethodAdapter(writer);
        reader.accept(pubMethAdapter, 0);
        return writer.toByteArray();
    }

}
