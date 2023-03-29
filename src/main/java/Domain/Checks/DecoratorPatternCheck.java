package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;

import java.util.ArrayList;
import java.util.List;

public class DecoratorPatternCheck implements Check{
    @Override
    public PresentationInformation check(CheckData data) {
        PresentationInformation presentationInformation = new PresentationInformation(CheckType.DecoratorPattern);
        List<ClassAdapter> classes = data.getClasses();
        for (ClassAdapter classAdapter : classes){
            boolean isAbstract = classAdapter.getIsAbstract();
            boolean extendsClass = !classAdapter.getExtends().equals("java/lang/Object");
            if (extendsClass && isAbstract){
                for (FieldAdapter fieldAdapter : classAdapter.getAllFields()){
                    String fieldType = this.parseType(fieldAdapter.getType());
                    String extendedClass = this.parseExtendedClass(classAdapter.getExtends());
                    if (fieldType.equals(extendedClass)){
                        for (ClassAdapter classAdapter2 : classes){
                            String className = this.parseExtendedClass(classAdapter2.getClassName());
                            if (className.equals(fieldType)){
                                if (classAdapter2.getIsAbstract()){
                                    presentationInformation.passedCheck();
                                    presentationInformation.addDisplayLine(classAdapter.getClassName() + " decorates " + classAdapter2.getClassName());
                                }
                            }
                        }
                    }
                }
            }
        }

        return presentationInformation;
    }

    private String parseType(String type){
        String fieldType = "";
        if (type.contains(".")){
            fieldType = type.split("\\.")[type.split("\\.").length - 1];
        }
        if (fieldType.contains(";")){
            fieldType = fieldType.replaceAll(";", "");
        }
        return fieldType;
    }

    private String parseExtendedClass(String extendedClass){
        String parsedExtendedClass = "";
        if (extendedClass.contains("/")){
            parsedExtendedClass = extendedClass.split("/")[extendedClass.split("/").length - 1];
        }
        return parsedExtendedClass;
    }



}
