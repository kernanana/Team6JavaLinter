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
    public PresentationInformation check(CheckData data) {
        PresentationInformation presentationInformation = new PresentationInformation();
        presentationInformation.checkName = CheckType.PoorNamingConvention;
        BadNames badNames = new BadNames();
        ArrayList<String> displayLines = new ArrayList<>();
        List<ClassAdapter> classes = data.getClasses();
        UserOptions userOptions = data.getUserOptions();
        //instantiate names into lists
        for (ClassAdapter classAdapter : classes) {
            badNames.changeClassAdapter(classAdapter);
            checkClass(classAdapter, presentationInformation, displayLines, badNames);
        }

        //do the check on fields
        if(userOptions.namingConventionAutoCorrect)
            presentationInformation = tryAutoCorrect(classes, badNames, presentationInformation);

        presentationInformation.displayLines = displayLines;
        return presentationInformation;
    }

    private void checkClass(ClassAdapter classAdapter, PresentationInformation presentationInformation, ArrayList<String> displayLines, BadNames badNames) {
        String className = classAdapter.getClassName();
        List<FieldAdapter> fields = classAdapter.getAllFields();
        List<MethodAdapter> methods = classAdapter.getAllMethods();
        badNames.changeClassAdapter(classAdapter);

        checkClassName(badNames, presentationInformation, displayLines, className);

        for (FieldAdapter field : fields) {
            checkFieldName(field, presentationInformation, displayLines, className, badNames);
        }

        for (MethodAdapter method : methods) {
            checkMethodName(method, presentationInformation, displayLines, className, badNames);
        }

    }

    private void checkMethodName(MethodAdapter method, PresentationInformation presentationInformation, ArrayList<String> displayLines, String className, BadNames badNames) {
        String mname = method.getMethodName();
        Matcher matcher = lowerCaseChar.matcher(mname.substring(0, 1));
        if(matcher.matches()){
            //did nothing before just added ""
        }
        else if (mname.equals("<init>")){
            //does nothing b/c a contructor must always be identical to its class name
        }
        else{
            badNames.addBadMethodName(method);
            displayLines.add("Method name "+mname+" in "+ className +" needs to be lowercased");
            presentationInformation.passed = true;
        }
    }

    private void checkFieldName(FieldAdapter field, PresentationInformation presentationInformation,
                                ArrayList<String> displayLines, String className, BadNames badNames) {
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
                badNames.addBadFieldName(field, false);
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
                    badNames.addBadFieldName(field, true);
                    displayLines.add("Field name "+fname+" in "+ className + " needs to be ALL CAPS with _ as spaces");
                    presentationInformation.passed = true;
                    break;
                }
            }
        }
    }

    private void checkClassName(BadNames badNames, PresentationInformation presentationInformation, ArrayList<String> displayLines, String className) {
        String trimClassName = className.substring(className.lastIndexOf("/") + 1);
        Matcher classNameCheck = upperCaseChar.matcher(trimClassName.substring(0,1));
        if (!classNameCheck.matches()){
            badNames.addBadClassName(trimClassName);
            displayLines.add("Class name " + trimClassName + " in "+ className + " needs to be uppercased");
            presentationInformation.passed = true;
        }
    }

    private PresentationInformation tryAutoCorrect(List<ClassAdapter> classes, BadNames badNames, PresentationInformation presentationInformation) {

        NamingConventionAutoCorrect autoCorrect = new NamingConventionAutoCorrect();
        try {
            AutoCorrectDataHolder data = new AutoCorrectDataHolder(classes, badNames);
            presentationInformation = autoCorrect.autoCorrect(data,presentationInformation);
            return presentationInformation;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

}

