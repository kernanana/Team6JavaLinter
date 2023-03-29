package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.util.ArrayList;
import java.util.List;

public class EqualsHashCodeCheck implements Check{

    @Override
    public PresentationInformation check(CheckData data) {
        PresentationInformation presentationInformation = new PresentationInformation();
        presentationInformation.checkName = CheckType.EqualsHashCode;
        ArrayList<String> displayLines = new ArrayList<>();
        presentationInformation.passed = true;
        List<ClassAdapter> classes = data.getClasses();
        for (ClassAdapter classAdapter : classes){
            ArrayList<MethodAdapter> methodAdapters = (ArrayList<MethodAdapter>) classAdapter.getAllMethods();
            boolean hasEquals = false;
            boolean hasHashCode = false;
            for (MethodAdapter methodAdapter : methodAdapters){
                if (methodAdapter.getMethodName().equals("equals")){
                    hasEquals = true;
                }
                if (methodAdapter.getMethodName().equals("hashCode")){
                    hasHashCode = true;
                }
            }
            if (hasEquals ^ hasHashCode){
                presentationInformation.passed = false;
                if (!hasEquals){
                    displayLines.add(classAdapter.getClassName() + " missing equals method");
                }else{
                    displayLines.add(classAdapter.getClassName() + " missing hashCode method");
                }
            }
        }
        presentationInformation.displayLines = displayLines;
        return presentationInformation;
    }

}

