package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NamingConventionCheck implements Check {

    private static final Pattern upperCaseChar = Pattern.compile("[A-Z]");
    private static final Pattern lowerCaseChar = Pattern.compile("[a-z]");
    private static final Pattern finalNaming = Pattern.compile("[A-Z_0-9]");

    @Override
    public PresentationInformation check(List<ClassAdapter> classes, UserOptions userOptions) {
        List<String> badClassNames = new ArrayList<>();
        Map<ClassAdapter, List<FieldAdapter>> badFieldNameMap = new HashMap<>();
        Map<ClassAdapter, List<FieldAdapter>> badFinalFieldNameMap = new HashMap<>();
        Map<ClassAdapter, List<MethodAdapter>> badMethodNameMap = new HashMap<>();
        PresentationInformation presentationInformation = new PresentationInformation();
        presentationInformation.checkName = CheckType.PoorNamingConvention;
        ArrayList<String> displayLines = new ArrayList<>();

        //instantiate names into lists
        for (ClassAdapter classAdapter : classes) {
            checkClass(classAdapter, presentationInformation, displayLines, badClassNames, badFieldNameMap, badFinalFieldNameMap, badMethodNameMap);
        }

        //do the check on fields
        if(userOptions.namingConventionAutoCorrect)
            presentationInformation = tryAutoCorrect(classes, badClassNames, badFieldNameMap, badFinalFieldNameMap, badMethodNameMap, presentationInformation);

        presentationInformation.displayLines = displayLines;
        return presentationInformation;
    }

    private void checkClass(ClassAdapter classAdapter, PresentationInformation presentationInformation, ArrayList<String> displayLines, List<String> badClassNames, Map<ClassAdapter, List<FieldAdapter>> badFieldNameMap, Map<ClassAdapter, List<FieldAdapter>> badFinalFieldNameMap, Map<ClassAdapter, List<MethodAdapter>> badMethodNameMap) {
        String className = classAdapter.getClassName();
        List<FieldAdapter> fields = classAdapter.getAllFields();
        List<MethodAdapter> methods = classAdapter.getAllMethods();

        List<FieldAdapter> badFieldNames = new ArrayList<>();
        List<MethodAdapter> badMethodNames = new ArrayList<>();
        List<FieldAdapter> badFinalFieldNames = new ArrayList<>();

        checkClassName(badClassNames, presentationInformation, displayLines, className);

        for (FieldAdapter field : fields) {
            checkFieldName(field, presentationInformation, displayLines, className, badFieldNames, badFinalFieldNames);
        }

        for (MethodAdapter method : methods) {
            checkMethodName(method, presentationInformation, displayLines, className, badMethodNames);
        }

        badFieldNameMap.put(classAdapter, badFieldNames);
        badFinalFieldNameMap.put(classAdapter, badFinalFieldNames);
        badMethodNameMap.put(classAdapter, badMethodNames);
    }

    private void checkMethodName(MethodAdapter method, PresentationInformation presentationInformation, ArrayList<String> displayLines, String className, List<MethodAdapter> badMethodNames) {
        String mname = method.getMethodName();
        Matcher matcher = lowerCaseChar.matcher(mname.substring(0, 1));
        if(matcher.matches()){
            //did nothing before just added ""
        }
        else if (mname.equals("<init>")){
            //does nothing b/c a contructor must always be identical to its class name
        }
        else{
            badMethodNames.add(method);
            displayLines.add("Method name "+mname+" in "+ className +" needs to be lowercased");
            presentationInformation.passed = true;
        }
    }

    private void checkFieldName(FieldAdapter field, PresentationInformation presentationInformation,
                                ArrayList<String> displayLines, String className, List<FieldAdapter> badFieldNames, List<FieldAdapter> badFinalFieldNames) {
        //add check for final fields
        boolean isFinal = field.getIsFinal();
        String fname = field.getFieldName();

        //nonfinal check
        if(!isFinal) {
            Matcher matcher = lowerCaseChar.matcher(fname.substring(0, 1));
            if(matcher.matches()){
                //did nothing before just added ""
            }
            else{
                badFieldNames.add(field);
                displayLines.add("Field name "+fname+" in "+ className +" needs to be lowercased");
                presentationInformation.passed = true;
            }
        }
        //final check
        else {
            for(int k=0; k<fname.length();k++) {
                Matcher fmatcher = finalNaming.matcher(fname.substring(k, k+1));
                if (fmatcher.matches()) {
                    //did nothing before just added ""
                } else {
                    badFinalFieldNames.add(field);
                    displayLines.add("Field name "+fname+" in "+ className + " needs to be ALL CAPS with _ as spaces");
                    presentationInformation.passed = true;
                    break;
                }
            }
        }
    }

    private void checkClassName(List<String> badClassNames, PresentationInformation presentationInformation, ArrayList<String> displayLines, String className) {
        String trimClassName = className.substring(className.lastIndexOf("/") + 1);
        Matcher classNameCheck = upperCaseChar.matcher(trimClassName.substring(0,1));
        if (!classNameCheck.matches()){
            badClassNames.add(trimClassName);
            displayLines.add("Class name " + trimClassName + " in "+ className + " needs to be uppercased");
            presentationInformation.passed = true;
        }
    }

    private PresentationInformation tryAutoCorrect(List<ClassAdapter> classes, List<String> badClassNames, Map<ClassAdapter, List<FieldAdapter>> badFieldNameMap, Map<ClassAdapter, List<FieldAdapter>> badFinalFieldNameMap, Map<ClassAdapter, List<MethodAdapter>> badMethodNameMap, PresentationInformation presentationInformation) {

        NamingConventionAutoCorrect autoCorrect = new NamingConventionAutoCorrect();
        try {
            return autoCorrect.autoCorrect(classes, badClassNames, badFieldNameMap, badFinalFieldNameMap, badMethodNameMap, presentationInformation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

}

