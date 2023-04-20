package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;

import java.util.ArrayList;
import java.util.List;

public class InformationHidingCheck implements Check{


    @Override
    public PresentationInformation check(CheckDataInterface data) {
        PresentationInformation presentationInformation = new PresentationInformation(CheckType.InformationHidingViolation);
        List<ClassAdapter> classes = data.getClasses();
        for (int i = 0; i < classes.size();i++){
            List<MethodAdapter> methods = classes.get(i).getAllMethods();
            int methodCount = methods.size() - 1;
            int getterSetterCount = 0;
            for (int j = 0; j<methods.size();j++){
                boolean isSetter = methods.get(j).isSetter();
                if(isSetter){
                    getterSetterCount++;
                    presentationInformation.addDisplayLine((methods.get(j).getMethodName() + " in " + classes.get(i).getClassName() +" is a Setter"));
                    presentationInformation.passedCheck();
                }
                else{
                    boolean isGetter = methods.get(j).isGetter();
                    if(isGetter){
                        getterSetterCount++;
                        presentationInformation.addDisplayLine(methods.get(j).getMethodName() + " in " + classes.get(i).getClassName() + " is a Getter");
                        presentationInformation.passedCheck();
                    }
                }
            }
            if(getterSetterCount == methodCount){
                presentationInformation.removeLastNDisplayLines(methodCount);
                presentationInformation.failedCheck();
                presentationInformation.addDisplayLine("Getter/Setter detected in " + classes.get(i).getClassName() + ", but ignored since it is a data class");
            }
        }
        return presentationInformation;
    }
}
