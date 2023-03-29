package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BadNames {

    private final List<String> badClassNames;
    private final Map<ClassAdapter, List<FieldAdapter>> badFieldNameMap;
    private final Map<ClassAdapter, List<FieldAdapter>> badFinalFieldNameMap;
    private final Map<ClassAdapter, List<MethodAdapter>> badMethodNameMap;
    private ClassAdapter currentAdapter;
    private List<FieldAdapter> currentFields;
    private List<FieldAdapter> currentFinalFields;
    private List<MethodAdapter> currentMethods;

    public BadNames() {
        badClassNames = new ArrayList<>();
        badFieldNameMap = new HashMap<>();
        badFinalFieldNameMap = new HashMap<>();
        badMethodNameMap = new HashMap<>();
    }

    public void addBadClassName(String className) {
        badClassNames.add(className);
    }

    public void addBadMethodName(MethodAdapter method) {
        currentMethods.add(method);
    }

    public void addBadFieldName(FieldAdapter field, boolean isFinal) {
        if(isFinal)
            currentFinalFields.add(field);
        else
            currentFields.add(field);
    }

    public void changeClassAdapter(ClassAdapter newAdapter) {
        saveCurrentAdapterData();
        currentAdapter = newAdapter;
        currentFields = new ArrayList<>();
        currentFinalFields = new ArrayList<>();
        currentMethods = new ArrayList<>();
    }

    public boolean hasClassNameViolations() {
        return badClassNames.isEmpty();
    }

    public Map<ClassAdapter, List<FieldAdapter>> getBadFields() {
        return badFieldNameMap;
    }

    public Map<ClassAdapter, List<MethodAdapter>> getBadMethods() {
        return badMethodNameMap;
    }

    public int getNumberOfBadClassNames() {
        return badClassNames.size();
    }

    private void saveCurrentAdapterData() {
        if(currentAdapter != null) {
            badFieldNameMap.put(currentAdapter, currentFields);
            badFinalFieldNameMap.put(currentAdapter, currentFinalFields);
            badMethodNameMap.put(currentAdapter, currentMethods);
        }
    }


}
