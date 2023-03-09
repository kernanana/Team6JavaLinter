package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.util.ArrayList;
import java.util.List;

public class InformationHidingCheck implements Check{


    @Override
    public PresentationInformation check(List<ClassAdapter> classes, UserOptions userOptions) {
        PresentationInformation presentationInformation = new PresentationInformation();
        presentationInformation.checkName = CheckType.InformationHidingViolation;
        ArrayList<String> displayLines = new ArrayList<>();
        for (int i = 0; i < classes.size();i++){
            List<MethodAdapter> methods = classes.get(i).getAllMethods();
            int methodCount = methods.size() - 1;
            int getterSetterCount = 0;
            for (int j = 0; j<methods.size();j++){
                boolean isSetter = methods.get(j).isSetter();
                if(isSetter){
                    getterSetterCount++;
                    displayLines.add((methods.get(j).getMethodName() + " in " + classes.get(i).getClassName() +" is a Setter"));
                    presentationInformation.passed = true;
                }
                else{
                    boolean isGetter = methods.get(j).isGetter();
                    if(isGetter){
                        getterSetterCount++;
                        displayLines.add(methods.get(j).getMethodName() + " in " + classes.get(i).getClassName() + " is a Getter");
                        presentationInformation.passed = true;
                    }
                }
            }
            if(getterSetterCount == methodCount){
                int start = displayLines.size()-methodCount;
                for(int k = start; k<getterSetterCount;k++){
                    displayLines.remove(start);
                }
                presentationInformation.passed = false;
                displayLines.add("Getter/Setter detected in " + classes.get(i).getClassName() + ", but ignored since it is a data class");
            }
        }
        presentationInformation.displayLines = displayLines;
        return presentationInformation;
    }
}
