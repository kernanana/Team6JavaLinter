package Domain.Checks;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import Domain.Adapters.*;
import Domain.PresentationInformation;
import org.objectweb.asm.Opcodes;

public class NamingConventionAutoCorrect {

    public NamingConventionAutoCorrect(){}

    public PresentationInformation autoCorrect(List<ClassAdapter> classes, List<String> badClassNames, Map<ClassAdapter, List<FieldAdapter>> badFieldNames, Map<ClassAdapter, List<FieldAdapter>> badFinalFieldNames, Map<ClassAdapter, List<MethodAdapter>> badMethodNames, PresentationInformation pi) throws IOException, NoSuchFieldException {
        //className autoCorrect
        for (ClassAdapter badClass:badFieldNames.keySet()){
            byte[] output;
            String className = badClass.getClassName();
            className = className.replace("/", ".");
            System.out.println(className);
            for(int i=0;i<badFieldNames.get(badClass).size();i++){
                String badName = badFieldNames.get(badClass).get(i).getFieldName();
                System.out.println("Need to remove: " + badName);
                String goodName = badName.substring(0,1).toLowerCase() + badName.substring(1);
                System.out.println("Change to: " + goodName);
                int accessType;
                if(badFieldNames.get(badClass).get(i).getIsPublic()){
                    accessType = Opcodes.ACC_PUBLIC;
                } else if (badFieldNames.get(badClass).get(i).getIsProtected()) {
                    accessType = Opcodes.ACC_PROTECTED;
                }
                else{
                    accessType = Opcodes.ACC_PRIVATE;
                }
                boolean removed = badClass.removeField(badName);
                System.out.println("Successfully removed: "+removed);

                System.out.println("Now adding " + goodName + " to " + className);
                output = badClass.addField(goodName, accessType, "I");
                Class c = badClass.constructClass(output);
            }

        }
        return pi;
    }
}
