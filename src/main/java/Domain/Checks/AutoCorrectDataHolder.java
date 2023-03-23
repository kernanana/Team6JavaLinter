package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;
import java.util.List;
import java.util.Map;

public class AutoCorrectDataHolder {
    private List<ClassAdapter> classNames;
    private List<String> badClassNames;
    private Map<ClassAdapter, List<FieldAdapter>> badFieldNames;
    private Map<ClassAdapter, List<FieldAdapter>> badFinalFieldNames;
    private Map<ClassAdapter, List<MethodAdapter>> badMethodNames;

    AutoCorrectDataHolder(List<ClassAdapter> classNames, List<String> badClassNames, Map<ClassAdapter, List<FieldAdapter>> badFieldNames,
                          Map<ClassAdapter, List<FieldAdapter>> badFinalFieldNames, Map<ClassAdapter, List<MethodAdapter>> badMethodNames){
        this.classNames = classNames;
        this.badClassNames = badClassNames;
        this.badFieldNames = badFieldNames;
        this.badFinalFieldNames = badFinalFieldNames;
        this.badMethodNames = badMethodNames;
        verifyDataFormat();
    };

    public Map<ClassAdapter, List<FieldAdapter>> getBadFields(){
        return badFieldNames;
    }

    public Map<ClassAdapter, List<MethodAdapter>> getBadMethodNames(){
        return badMethodNames;
    }

    public  boolean areThereClassNameViolations(){
        if(badClassNames.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean verifyDataFormat(){
//        if(classNames.size()){
//
//        }
        if(classNames.size() == badClassNames.size()){
            return true;
        }
        return false;
    }
}
