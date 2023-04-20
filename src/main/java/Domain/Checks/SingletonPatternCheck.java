package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.util.ArrayList;
import java.util.List;

public class SingletonPatternCheck implements Check {

    @Override
    public PresentationInformation check(CheckDataInterface data) {
        PresentationInformation presentationInformation = new PresentationInformation(CheckType.SingletonPattern);
        List<ClassAdapter> classes = data.getClasses();

        for (ClassAdapter classAdapter : classes) {
            if (isSingleton(classAdapter)) {
                presentationInformation.addDisplayLine("Singleton Pattern detected for " + classAdapter.getClassName());
                presentationInformation.passedCheck();
            }
        }

        return presentationInformation;
    }

    private boolean isSingleton(ClassAdapter classAdapter) {
        boolean hasStaticFieldofItself = false;
        boolean constructorIsPrivate = false;
        boolean getInstanceReturnsItself = false;

        String className = getClassName(classAdapter);
        List<FieldAdapter> fields = classAdapter.getAllFields();
        for (FieldAdapter fieldAdapter : fields) {
            String fieldType = getFieldType(fieldAdapter);
            if (fieldType.equals(className) && fieldAdapter.getIsStatic()) {
                hasStaticFieldofItself = true;
                break;
            }
        }

        if (hasStaticFieldofItself) {
            List<MethodAdapter> methods = classAdapter.getAllMethods();
            for (MethodAdapter methodAdapter : methods) {
                String methodName = methodAdapter.getMethodName();
                if (methodName.equals("<init>") && methodAdapter.getIsPrivate()) {
                    constructorIsPrivate = true;
                }
                String methodReturn = getMethodReturnType(methodAdapter);
                if (methodAdapter.isStatic() && methodAdapter.getIsPublic() && methodReturn.equals(className)) {
                    getInstanceReturnsItself = true;
                }
            }
        }

        return hasStaticFieldofItself && constructorIsPrivate && getInstanceReturnsItself;
    }

    private String getClassName(ClassAdapter classAdapter) {
        String className = classAdapter.getClassName();
        return className.substring(className.lastIndexOf("/") + 1);
    }

    private String getFieldType(FieldAdapter fieldAdapter) {
        String fieldType = fieldAdapter.getType();
        return fieldType.substring(fieldType.lastIndexOf(".") + 1, fieldType.length()-1);
    }

    private String getMethodReturnType(MethodAdapter methodAdapter) {
        String methodReturn = methodAdapter.getReturnType();
        return methodReturn.substring(methodReturn.lastIndexOf(".") + 1);
    }
}