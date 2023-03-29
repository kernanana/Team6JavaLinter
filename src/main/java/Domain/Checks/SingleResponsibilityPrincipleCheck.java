package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SingleResponsibilityPrincipleCheck implements Check{
    private int maximumMethods;


    @Override
    public PresentationInformation check(CheckData data) {
        List<ClassAdapter> classes = data.getClasses();
        UserOptions userOptions = new UserOptions();

        if(data.hasUserOptions()) {
            userOptions = data.getUserOptions();
        }

        PresentationInformation presentationInformation = new PresentationInformation(CheckType.SingleResponsibilityPrinciple);

        presentationInformation.passedCheck();

        HashMap<String,Integer> classNameToCount = new HashMap<>();
        for (ClassAdapter classAdapter : classes){
            ArrayList<MethodAdapter> methodAdapters = (ArrayList<MethodAdapter>) classAdapter.getAllMethods();
            int count = 0;
            for (MethodAdapter methodAdapter : methodAdapters){
                if (methodAdapter.getIsPublic() && !methodAdapter.getMethodName().equals("<init>")){
                    count++;
                }
            }
            classNameToCount.put(classAdapter.getClassName(), count);
        }

        boolean didSetMax = true;
        this.maximumMethods = userOptions.getMaximumMethods();

        if (!userOptions.maxMethodsIsDefined()){
            didSetMax = false;
            ArrayList<Integer> methodsAsList = new ArrayList<Integer>(classNameToCount.values());
            Collections.sort(methodsAsList);
            this.maximumMethods = methodsAsList.get(methodsAsList.size()/2);
        }
        if (!didSetMax){
            presentationInformation.addDisplayLine("The median amount of public methods is " + this.maximumMethods +
                    ". We will consider over " + this.maximumMethods + " public methods a violation");
        }
        else{
            presentationInformation.addDisplayLine("The maximum amount of public methods is " + this.maximumMethods +
                    ". We will consider over " + this.maximumMethods + " public methods a violation");
        }
        for (String key : classNameToCount.keySet()) {
            if (classNameToCount.get(key) > this.maximumMethods){
                presentationInformation.failedCheck();
                presentationInformation.addDisplayLine(key + " has " + Integer.toString(classNameToCount.get(key)) + " public methods");
            }
        }

        return presentationInformation;
    }
}
