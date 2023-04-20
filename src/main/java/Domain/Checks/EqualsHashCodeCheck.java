package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;

import java.util.ArrayList;
import java.util.List;

public class EqualsHashCodeCheck implements Check{

    @Override
    public PresentationInformation check(CheckDataInterface data) {
        PresentationInformation presentationInformation = new PresentationInformation(CheckType.EqualsHashCode);
        presentationInformation.passedCheck();
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
                presentationInformation.failedCheck();
                if (!hasEquals){
                    presentationInformation.addDisplayLine(classAdapter.getClassName() + " missing equals method");
                }else{
                    presentationInformation.addDisplayLine(classAdapter.getClassName() + " missing hashCode method");
                }
            }
        }

        return presentationInformation;
    }

}

