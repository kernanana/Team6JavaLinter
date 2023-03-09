package ASMPracticeCode.ObserverDummyData;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static ASMPracticeCode.Main.*;

public class Main {
    public static void main(String args[]) {
        System.out.println("---Running Dummy Data-------");
        Observer obs = new Observer();
        Observee add5 = new ObserveeAdd5(0);
        Observee sub3 = new ObserveeSub3(0);
        obs.add(add5);
        obs.add(sub3);
        obs.displayAll();
        obs.updateAll();
        obs.displayAll();
        System.out.println("---Running ASM Analysis-------");
        try {
            File folder = new File("C:\\3rd_Year_RHIT_Classes\\CSSE374\\FinalProject\\project202320-s1s2-team08\\src\\main\\java\\ASMPracticeCode\\ObserverDummyData");
            ArrayList<String> fileNames = listFilesForFolder(folder);
            //1. ClassReader: data reader
            ClassReader reader = new ClassReader("ASMPracticeCode.ObserverDummyData.Observer");
            //2. ClassNode: an empty data container
            ClassNode classNode = new ClassNode();
            // ALWAYS PASS EXPANDFRAMES
            reader.accept(classNode, ClassReader.EXPAND_FRAMES);

            System.out.println();

            checkForAbstractListField(classNode);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public static ArrayList<String> listFilesForFolder(File folder) {
        ArrayList<String> files = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
                files.add(fileEntry.getName());
            }
        }
        return files;
    }

    private static ArrayList<String> checkForAbstractListField(ClassNode classNode) {
        // Print all fields (note the cast; ASM doesn't store generic data with its Lists)
        List<FieldNode> fields = (List<FieldNode>) classNode.fields;
        for (FieldNode field : fields) {
            System.out.println("	Field: " + field.name);
            System.out.println("	Internal JVM type: " + (field.desc));
            System.out.println("	User-friendly type: "
                    + Type.getObjectType(field.desc).getClassName());
            // Query the access modifiers with the ACC_* constants.

            System.out.println("	public? "
                    + ((field.access & Opcodes.ACC_PUBLIC) != 0));

            System.out.println("is Array or ArrayList: " + (Type.getObjectType(field.desc).getSort() == 10));
            // look up https://asm.ow2.io/javadoc/org/objectweb/asm/Type.html to get opcodes for a fieldtype
            boolean isList = (Type.getObjectType(field.desc).getSort() == 10);
            boolean isAbstractType = true;
            if (isList & isAbstractType) {
                System.out.println("has list of observees");
            }
            // TODO: how do you tell if something has package-private access? (ie no access modifiers?)

            // TODO: how do I write a lint check to tell if this field has a bad name?

            System.out.println();
        }
        return null;
    }
}
