package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;

import java.util.ArrayList;
import java.util.List;

public class NoFinalizerCheck implements Check{
    @Override
    public PresentationInformation check(CheckDataInterface data) {
        PresentationInformation info = new PresentationInformation(CheckType.NoFinalizerCheck);
        info.passedCheck();
        List<ClassAdapter> classes = data.getClasses();

        for (ClassAdapter aClass : classes) {
            boolean foundFinalize = lookForFinalize(aClass);
            if (foundFinalize) {
                String str = "Class: " + aClass.getClassName() + " contains finalize method with zero parameters";
                info.addDisplayLine(str);
            }
        }

        if (info.countDisplayLines() > 0) {
            info.failedCheck();
        }
        return info;
    }

    private boolean lookForFinalize(ClassAdapter classAdapter) {
        List<MethodAdapter> methods = classAdapter.getAllMethods();
        for (MethodAdapter method : methods) {
            if (method.getMethodName().equals("finalize")) {
                if (method.getArgTypes().size() == 0) {
                    return true;
                }
            }
        }
        return false;
    }



}
