package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class HollywoodPrincipleCheck implements Check{
    @Override
    public PresentationInformation check(List<ClassAdapter> classes, UserOptions userOptions) {
        PresentationInformation info = new PresentationInformation();
        info.passed = true;

        List<String> displayInfo = new ArrayList<>();
        for (int x = 0; x < classes.size(); x ++) {
            ClassAdapter highLevelClass = getHighLevelClass(classes.get(x), classes);
            if (!isNull(highLevelClass)){
                boolean dependency = checkDependsOn(highLevelClass, classes.get(x));
                if (dependency){
                    info.passed = false;
                    displayInfo.add(formatLine(highLevelClass, classes.get(x)));
                }
            }
        }

        for (int x = 0; x < classes.size(); x ++) {
            List <ClassAdapter> highLevelClasses = getHighLevelClasses(classes.get(x), classes);
            for (int i = 0; i < highLevelClasses.size(); i ++) {
                boolean dependency = checkDependsOn(highLevelClasses.get(i), classes.get(x));
                if (dependency){
                    info.passed = false;
                    displayInfo.add(formatLine(highLevelClasses.get(i), classes.get(x)));
                }
            }
        }

        info.checkName = CheckType.HollywoodPrinciple;
        info.displayLines = displayInfo;
        return info;
    }

    private String formatLine(ClassAdapter highLevelClass, ClassAdapter lowLevelClass){
        String str = "High level class: " + highLevelClass.getClassName() + " is called by low level class: " + lowLevelClass.getClassName();
        return str;
    }
    private boolean checkDependsOn(ClassAdapter highLevelClass, ClassAdapter lowLevelClass){
        //Check if any of the methods contain highLevelClass.name
        String highLevelClassName = highLevelClass.getClassName();
        List<MethodAdapter> lowLevelMethods = lowLevelClass.getAllMethods();

        for (MethodAdapter lowLevelMethod : lowLevelMethods) {
            boolean foundDependency = checkAllInstructionsForDependency(highLevelClassName, lowLevelMethod.getInstructions());
            if (foundDependency) {
                return true;
            }
        }

        return false;
    }

    private boolean checkAllInstructionsForDependency(String className, List<String> instructions) {
        for (int x = 0; x < instructions.size(); x ++) {
            //This allows lower level classes to call the high level classes constructor
            if (instructions.get(x).contains(className) && !instructions.get(x).contains("<init>")) {
                return true;
            }
        }
        return false;
    }

    private ClassAdapter getHighLevelClass(ClassAdapter lowLevelClass, List<ClassAdapter> allClasses) {
        String highLevelName = lowLevelClass.getExtends();
        for (ClassAdapter allClass : allClasses) {
            if (allClass.getClassName().equals(highLevelName)) {
                return allClass;
            }
        }
        return null;
    }

    private List<ClassAdapter> getHighLevelClasses(ClassAdapter lowLevelClass, List<ClassAdapter> allClasses) {
        String lowLevelName = lowLevelClass.getClassName();
        List<ClassAdapter> highLevelClasses = new ArrayList<>();
        for (ClassAdapter allClass : allClasses) {
            List<FieldAdapter> fields = allClass.getAllFields();
            for (FieldAdapter field : fields) {
                String fieldType = convertTypeToName(field.getType());
                if (fieldType.equals(lowLevelName)) {
                    //High level class has a field of type low level class
                    highLevelClasses.add(allClass);
                }
            }
        }
        return highLevelClasses;
    }

    private String convertTypeToName(String type){
        String temp = type.substring(1, type.length() - 1);
        String temp1 = temp.replaceAll("\\.", "/");
        return temp1;
    }
}
