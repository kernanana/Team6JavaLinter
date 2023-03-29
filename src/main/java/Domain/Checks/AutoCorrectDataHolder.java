package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;
import java.util.List;
import java.util.Map;

public class AutoCorrectDataHolder {
    private List<ClassAdapter> classNames;
    private BadNames badNames;

    AutoCorrectDataHolder(List<ClassAdapter> classNames, BadNames badNames){
        this.classNames = classNames;
        this.badNames = badNames;
        verifyDataFormat();
    };

    public Map<ClassAdapter, List<FieldAdapter>> getBadFields(){
        return badNames.getBadFields();
    }

    public Map<ClassAdapter, List<MethodAdapter>> getBadMethodNames(){
        return badNames.getBadMethods();
    }

    public  boolean areThereClassNameViolations(){
        return badNames.hasClassNameViolations();
    }

    public boolean verifyDataFormat(){
        return classNames.size() == badNames.getNumberOfBadClassNames();
    }
}
