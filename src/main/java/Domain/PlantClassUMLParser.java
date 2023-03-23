package Domain;

import DataSource.SourceStringReaderAdapter;
import DataSource.UMLTextWriter;
import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlantClassUMLParser {

    private SourceStringReaderAdapter sourceStringReaderAdapter;
    private UMLTextWriter umlTextWriter;
    public PlantClassUMLParser(SourceStringReaderAdapter sourceStringReaderAdapter, UMLTextWriter umlTextWriter){
        this.sourceStringReaderAdapter = sourceStringReaderAdapter;
        this.umlTextWriter = umlTextWriter;
    }

    public String parseUML(List<ClassAdapter> classes, String outPutFilePath) {
        StringBuilder umlString = new StringBuilder();
        umlString.append("@startuml\n");
        ArrayList<String> hasAs = new ArrayList<>();
        ArrayList<String> isAs = new ArrayList<>();
        ArrayList<String> dependsOn = new ArrayList<>();
        ArrayList<String> classesInProject = new ArrayList<>();
        HashMap<String, String> singleLetterMappings= new HashMap<>();
        singleLetterMappings.put("Z", "boolean");
        singleLetterMappings.put("I", "int");
        singleLetterMappings.put("D", "int");
        singleLetterMappings.put("F", "float");
        for (ClassAdapter classAdapter : classes){
            boolean isEnum = classAdapter.getIsEnum();
            StringBuilder classString = new StringBuilder();
            String className = classAdapter.getClassName();
            ArrayList<String> alreadyDependent = new ArrayList<>();

            if (classAdapter.getClassName().contains("/")){
                className = classAdapter.getClassName().split("/")[classAdapter.getClassName().split("/").length - 1];
            }

            classString.append(getPrefix(classAdapter, className));
            classesInProject.add(className);

            if (isEnum) {
                writeEnumClass(umlString, classAdapter, classString);
            } else {
                for (FieldAdapter fieldAdapter : classAdapter.getAllFields()) {
                    if (fieldAdapter.getIsPublic()) {
                        classString.append("+");
                    } else {
                        classString.append("-");
                    }
                    String fieldType = fieldAdapter.getType();
                    if (fieldAdapter.getType().contains(".")) {
                        fieldType = fieldAdapter.getType().split("\\.")[fieldAdapter.getType().split("\\.").length - 1];
                    }
                    if (fieldType.contains(";")) {
                        fieldType = fieldType.replaceAll(";", "");
                    }
                    if (singleLetterMappings.containsKey(fieldType)) {
                        fieldType = singleLetterMappings.get(fieldType);
                    }
                    classString.append("{field} " + fieldType + " " + fieldAdapter.getFieldName() + "\n");
                    hasAs.add(className + " -> " + fieldType);
                }

                for (MethodAdapter methodAdapter : classAdapter.getAllMethods()){
                    if (methodAdapter.getIsPublic()){
                        classString.append("+");
                    }else{
                        classString.append("-");
                    }
                    if (methodAdapter.getMethodName().equals("<init>")){
                        classString.append("{method} " + className + "(");
                    }else if (methodAdapter.getIsAbstract()){
                        classString.append("{abstract} " + methodAdapter.getMethodName() + "(");
                    }else{
                        classString.append("{method} " + methodAdapter.getMethodName() + "(");
                    }
                    if (methodAdapter.getArgTypes().size() == 0){
                        classString.append("):");
                    }
                    for (int i = 0; i < methodAdapter.getArgTypes().size(); i++) {
                        String argString = methodAdapter.getArgTypes().get(i);
                        if (argString.contains(".")){
                            argString = argString.split("\\.")[argString.split("\\.").length - 1];
                        }
                        if (i == methodAdapter.getArgTypes().size() - 1) {
                            classString.append(argString + "):");
                        } else {
                            classString.append(argString + ", ");
                        }
                        if (!alreadyDependent.contains(argString)){
                            dependsOn.add(className + " .> " + argString);
                            alreadyDependent.add(argString);
                        }
                    }
                    String returnType = methodAdapter.getReturnType();
                    if (returnType.contains(".")){
                        returnType = methodAdapter.getReturnType().split("\\.")[methodAdapter.getReturnType().split("\\.").length - 1];
                    }
                    classString.append(returnType + "\n");
                    if (!alreadyDependent.contains(returnType)){
                        dependsOn.add(className + " .> " + returnType);
                        alreadyDependent.add(returnType);
                    }
                }
                for (String interfaceString : classAdapter.getInterfaces()){
                    isAs.add(interfaceString + " <|-- " + className);
                }
                isAs.add(classAdapter.getExtends() + " <|-- " +  className);
                classString.append("}\n");
                umlString.append(classString);
            }
        }
        isAs.addAll(hasAs);
        isAs.addAll(dependsOn);
        for (String arrow : isAs){
            String[] arrowParts = arrow.split(" ");
            String left = arrowParts[0];
            String middle = arrowParts[1];
            String right = arrowParts[2];
            if (left.contains("/")){
                left = left.split("/")[left.split("/").length - 1];
            }
            if (classesInProject.contains(left) && classesInProject.contains(right)){
                umlString.append(left).append(" ").append(middle).append(" ").append(right).append("\n");
            }
        }
        umlString.append("@enduml\n");
        SourceStringReader reader = new SourceStringReader(umlString.toString());
        sourceStringReaderAdapter.generateImage(umlString.toString(), new File(outPutFilePath + "\\generatedUML.PNG"));
        this.umlTextWriter.writeUMLText(outPutFilePath, umlString.toString());

        System.out.println("\nGenerated UML! Notice that the image sized is capped, but you can always use the text to generate a larger image on your own.\n");
        return umlString.toString();
    }

    private static void writeEnumClass(StringBuilder umlString, ClassAdapter classAdapter, StringBuilder classString) {
        for (int i = 0; i < classAdapter.getAllFields().size(); i++){
            FieldAdapter fieldAdapter = classAdapter.getAllFields().get(i);
            if (!fieldAdapter.getFieldName().equals("$VALUES")){
                if (i == classAdapter.getAllFields().size() - 2){
                    classString.append(fieldAdapter.getFieldName() + "\n");
                }else {
                    classString.append(fieldAdapter.getFieldName() + ",");
                }
            }
        }
        classString.append("}\n");
        umlString.append(classString);
    }

    private String getPrefix(ClassAdapter classAdapter, String className) {
        String classString;
        if (classAdapter.getIsInterface()){
            classString = "interface " + className + "{\n";
        }else if (classAdapter.getIsAbstract()){
            classString = "abstract " + className + "{\n";
        }else if (classAdapter.getIsEnum()){
            classString = "Enum " + className + "{\n";
        }else{
            classString = "class " + className + "{\n";
        }
        return classString;
    }


}
