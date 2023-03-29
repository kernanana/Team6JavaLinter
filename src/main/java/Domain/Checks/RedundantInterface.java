package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.util.ArrayList;
import java.util.List;

public class RedundantInterface implements Check {

    @Override
    public PresentationInformation check(CheckData checkData) {
        PresentationInformation presentationInformation = new PresentationInformation(CheckType.RedundantInterface);
        presentationInformation.passedCheck();
        
        for (ClassAdapter classAdapter : checkData.getClasses()){
            checkClass(checkData.getClasses(), presentationInformation, classAdapter);
        }

        return presentationInformation;
    }

    private void checkClass(List<ClassAdapter> classes, PresentationInformation presentationInformation, ClassAdapter classAdapter) {

        if((classAdapter.getIsInterface() || classAdapter.getIsAbstract()) || classAdapter.getInterfaces().size() == 0) {
            return;
        }

        List<String> interfaces = classAdapter.getInterfaces();
        ClassAdapter cd = getClassAdapter(classes, classAdapter.getExtends());

        while(cd != null) {

            for(String interfaceName : interfaces) {
                ClassAdapter interfaceClass = getClassAdapter(classes, interfaceName);

                for(MethodAdapter interfaceMethodData : interfaceClass.getAllMethods()) {
                    for(MethodAdapter classMethodData : cd.getAllMethods()) {
                        if(interfaceMethodData.getSignature().equals(classMethodData.getSignature())) {
                            presentationInformation.failedCheck();
                            presentationInformation.addDisplayLine("Class " + classAdapter.getClassName() + " both inherits and implements method \"" + classMethodData.getSignature() + "\" from class " + cd.getClassName() +
                                    " and interface " + interfaceClass.getClassName());
                        }
                    }
                }
            }

            if(cd.getExtends().equals("java/lang/Object")) {
                break;
            }

            ClassAdapter parent = getClassAdapter(classes, cd.getExtends());
            cd = parent;
        }

    }

    private static ClassAdapter getClassAdapter(List<ClassAdapter> classes, String anExtends) {
        for(ClassAdapter classAdapter : classes) {
            if(classAdapter.getClassName().equals(anExtends)) {
                return classAdapter;
            }
        }

        return null;
    }
}
