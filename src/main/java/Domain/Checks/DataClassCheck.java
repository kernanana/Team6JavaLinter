package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;

import java.util.List;

public class DataClassCheck implements Check{

    @Override
    public PresentationInformation check(CheckDataInterface data) {
        PresentationInformation pi = new PresentationInformation(CheckType.DataClass);
        List<ClassAdapter> classes = data.getClasses();
        for(int i = 0; i<classes.size();i++){
            List<MethodAdapter> methods = classes.get(i).getAllMethods();
            boolean allDataMethods = checkIfAllGettersAndSetters(methods);
            if(allDataMethods){
                pi.addDisplayLine(classes.get(i).getClassName() + " is a Data Class");
                pi.passedCheck();
            }
        }
        return pi;
    }

    private boolean checkIfAllGettersAndSetters(List<MethodAdapter> methods) {
        for(int i = 1; i < methods.size(); i++){
            if(!(methods.get(i).isSetter() || methods.get(i).isGetter())){
                return false;
            }
        }
        return true;
    }
}
