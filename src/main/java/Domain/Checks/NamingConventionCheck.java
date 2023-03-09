package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NamingConventionCheck implements Check{


    @Override
    public PresentationInformation check(List<ClassAdapter> classes, UserOptions userOptions) {
        List<String> badClassNames = new ArrayList<>();
        Map<ClassAdapter, List<FieldAdapter>> badFieldNameMap = new HashMap<>();
        Map<ClassAdapter, List<FieldAdapter>> badFinalFieldNameMap = new HashMap<>();
        Map<ClassAdapter, List<MethodAdapter>> badMethodNameMap = new HashMap<>();
        PresentationInformation presentationInformation = new PresentationInformation();
        presentationInformation.checkName = CheckType.PoorNamingConvention;
        ArrayList<String> displayLines = new ArrayList<>();

        Pattern upperCaseChar = Pattern.compile("[A-Z]");
        Pattern lowerCaseChar = Pattern.compile("[a-z]");
        Pattern finalNaming = Pattern.compile("[A-Z_0-9]");

        //instantiate names into lists
        for(int i = 0; i < classes.size(); i++){
            String className = classes.get(i).getClassName();
            List<FieldAdapter> fields = classes.get(i).getAllFields();
            List<MethodAdapter> methods = classes.get(i).getAllMethods();

            List<FieldAdapter> badFieldNames = new ArrayList<>();
            List<MethodAdapter> badMethodNames = new ArrayList<>();
            List<FieldAdapter> badFinalFieldNames = new ArrayList<>();

            String trimClassName = className.substring(className.lastIndexOf("/") + 1);
            Matcher classNameCheck = upperCaseChar.matcher(trimClassName.substring(0,1));
            if (!classNameCheck.matches()){
                badClassNames.add(trimClassName);
                displayLines.add("Class name " + trimClassName + " in "+className + " needs to be uppercased");
                presentationInformation.passed = true;
            }

            for(int j = 0; j < fields.size(); j++){
                //add check for final fields
                boolean isFinal = fields.get(j).getIsFinal();
                String fname = fields.get(j).getFieldName();
                //System.out.println(fname + " is Final: " + isFinal);
                //nonfinal check
                if(!isFinal){
                    Matcher matcher = lowerCaseChar.matcher(fname.substring(0, 1));
                    if(matcher.matches()){
                        //did nothing before just added ""
                    }
                    else{
                        badFieldNames.add(fields.get(j));
                        displayLines.add("Field name "+fname+" in "+ className +" needs to be lowercased");
                        presentationInformation.passed = true;
                    }
                }
                //final check
                else{
                    for(int k=0; k<fname.length();k++) {
                        Matcher fmatcher = finalNaming.matcher(fname.substring(k, k+1));
                        if (fmatcher.matches()) {
                            //did nothing before just added ""
                        } else {
                            badFinalFieldNames.add(fields.get(k));
                            displayLines.add("Field name "+fname+" in "+ className + " needs to be ALL CAPS with _ as spaces");
                            presentationInformation.passed = true;
                            break;
                        }
                    }
                }
            }

            for(int j = 0; j < methods.size(); j++){
                String mname = methods.get(j).getMethodName();
                Matcher matcher = lowerCaseChar.matcher(mname.substring(0, 1));
                if(matcher.matches()){
                    //did nothing before just added ""
                }
                else if (mname.equals("<init>")){
                    //does nothing b/c a contructor must always be identical to its class name
                }
                else{
                    badMethodNames.add(methods.get(j));
                    displayLines.add("Method name "+mname+" in "+ className +" needs to be lowercased");
                    presentationInformation.passed = true;
                }
            }
            badFieldNameMap.put(classes.get(i), badFieldNames);
            badFinalFieldNameMap.put(classes.get(i), badFinalFieldNames);
            badMethodNameMap.put(classes.get(i), badMethodNames);
        }

        //do the check on fields
        if(userOptions.namingConventionAutoCorrect){
            NamingConventionAutoCorrect autoCorrect = new NamingConventionAutoCorrect();
            try {
                presentationInformation = autoCorrect.autoCorrect(classes, badClassNames, badFieldNameMap, badFinalFieldNameMap, badMethodNameMap, presentationInformation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        presentationInformation.displayLines = displayLines;
        return presentationInformation;
    }



}

