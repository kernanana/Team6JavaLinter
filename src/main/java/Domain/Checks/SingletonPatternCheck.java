package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.util.ArrayList;
import java.util.List;

public class SingletonPatternCheck implements Check{


    @Override
    public PresentationInformation check(List<ClassAdapter> classes, UserOptions userOptions) {
        PresentationInformation presentationInformation = new PresentationInformation();
        presentationInformation.checkName = CheckType.SingletonPattern;
        ArrayList<String> displayLines = new ArrayList<>();


        for(int i=0;i<classes.size();i++){
            boolean hasStaticFieldofItself = false;
            boolean constructorIsPrivate = false;
            boolean getInstanceReturnsItself = false;

            String className = classes.get(i).getClassName();
            className = className.substring(className.lastIndexOf("/") + 1);
            //System.out.println("Class Name: "+className);
            //check for static fields
            List<FieldAdapter> fields = classes.get(i).getAllFields();
            for(int j = 0; j < fields.size(); j++){
                String fieldType = fields.get(j).getType();
                fieldType = fieldType.substring(fieldType.lastIndexOf(".") + 1, fieldType.length()-1);
                //System.out.println("Field Name: " +fieldType);
                if(fieldType.equals(className)){
                    if(fields.get(j).getIsStatic()){
                        hasStaticFieldofItself = true;
                        //System.out.println(className + " passed pt1");
                        break;
                    }
                }
            }
            if (hasStaticFieldofItself == false){}
            else{
                List<MethodAdapter> methods = classes.get(i).getAllMethods();
                for (int j=0; j<methods.size(); j++){
                    String methodName = methods.get(j).getMethodName();
                    //System.out.println(methodName);
                    if(methodName.equals("<init>")){
                        if(methods.get(j).getIsPrivate()){
                            //System.out.println(className + " passed pt2");
                            constructorIsPrivate = true;
                        }
                    }
                    String methodReturn = methods.get(j).getReturnType();
                    methodReturn = methodReturn.substring(methodReturn.lastIndexOf(".")+1, methodReturn.length());
                    //System.out.println(methodName + "'s return type is: "+ methodReturn);
                    if(methods.get(j).isStatic() && methods.get(j).getIsPublic() && methodReturn.equals(className)){
                        //System.out.println(className + " passed pt3");
                        getInstanceReturnsItself = true;
                    }
                }
            }
            if(hasStaticFieldofItself && constructorIsPrivate && getInstanceReturnsItself){
                displayLines.add("Singleton Pattern detected for " + classes.get(i).getClassName());
                presentationInformation.passed = true;
            }
        }
        presentationInformation.displayLines = displayLines;
        return presentationInformation;
    }
}
