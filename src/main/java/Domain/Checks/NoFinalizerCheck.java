package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.util.ArrayList;
import java.util.List;

public class NoFinalizerCheck implements Check{
    @Override
    public PresentationInformation check(List<ClassAdapter> classes, UserOptions userOptions) {
        PresentationInformation info = new PresentationInformation();
        info.checkName = CheckType.NoFinalizerCheck;
        info.passed = true;
        List<String> displayInfo = new ArrayList<>();
        info.displayLines = displayInfo;
        for (ClassAdapter aClass : classes) {
            boolean foundFinalize = lookForFinalize(aClass);
            if (foundFinalize) {
                String str = "Class: " + aClass.getClassName() + " contains finalize method with zero parameters";
                displayInfo.add(str);
            }
        }

        if (displayInfo.size() > 0) {
            info.passed = false;
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
