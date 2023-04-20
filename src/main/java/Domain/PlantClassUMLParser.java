package Domain;

import DataSource.SourceStringReaderAdapter;
import DataSource.UMLTextWriter;
import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantClassUMLParser {
    public String parseUML(List<ClassAdapter> classes, String outPutFilePath) {
        StringBuilder umlString = new StringBuilder("@startuml\n");
        initLists();
        findClasses(classes, umlString);
        isAs.addAll(hasAs);
        isAs.addAll(dependsOn);
        for (String arrow : isAs){
            parseArrow(umlString, classesInProject, arrow);
        }
        umlString.append("@enduml\n");
        writeUMLImage(umlString, outPutFilePath);

        System.out.println("\nGenerated UML! Notice that the image sized is capped, but you can always use the text to generate a larger image on your own.\n");
        return umlString.toString();
    }

    private static final Map<String, String> singleLetterMappings = new HashMap<>();

    static {
        singleLetterMappings.put("Z", "boolean");
        singleLetterMappings.put("I", "int");
        singleLetterMappings.put("D", "int");
        singleLetterMappings.put("F", "float");
    }

    private ArrayList<String> hasAs, isAs, dependsOn, classesInProject;
    private SourceStringReaderAdapter sourceStringReaderAdapter;
    private UMLTextWriter umlTextWriter;
    public PlantClassUMLParser(SourceStringReaderAdapter sourceStringReaderAdapter, UMLTextWriter umlTextWriter){
        this.sourceStringReaderAdapter = sourceStringReaderAdapter;
        this.umlTextWriter = umlTextWriter;
    }


    private void findClasses(List<ClassAdapter> classes, StringBuilder umlString) {
        for (ClassAdapter classAdapter : classes) {
            boolean isEnum = classAdapter.getIsEnum();
            StringBuilder classString = new StringBuilder();
            String className = classAdapter.getClassName();
            ArrayList<String> alreadyDependent = new ArrayList<>();

            className = getProperClassName(classAdapter, className);
            classString.append(getClassPrefix(classAdapter, className));
            classesInProject.add(className);

            handleEnum(umlString, classAdapter, isEnum, classString, className, alreadyDependent);
        }
    }

    private void handleEnum(StringBuilder umlString, ClassAdapter classAdapter, boolean isEnum, StringBuilder classString, String className, ArrayList<String> alreadyDependent) {
        if (isEnum) {
            appendEnumClass(umlString, classAdapter, classString);
        } else {
            for (FieldAdapter fieldAdapter : classAdapter.getAllFields()) {
                appendParsedField(fieldAdapter, classString, className, singleLetterMappings);
            }
            for (MethodAdapter methodAdapter : classAdapter.getAllMethods()){
                appendParsedMethod(methodAdapter, classString, className, alreadyDependent);
            }
            for (String interfaceString : classAdapter.getInterfaces()){
                isAs.add(interfaceString + " <|-- " + className);
            }
            isAs.add(classAdapter.getExtends() + " <|-- " + className);
            classString.append("}\n");
            umlString.append(classString);
        }
    }

    private String getProperClassName(ClassAdapter classAdapter, String className) {
        if (classAdapter.getClassName().contains("/")){
            className = classAdapter.getClassName().split("/")[classAdapter.getClassName().split("/").length - 1];
        }
        return className;
    }

    private void parseArrow(StringBuilder umlString, ArrayList<String> classesInProject, String arrow) {
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

    private void writeUMLImage(StringBuilder umlString, String outPutFilePath) {
        sourceStringReaderAdapter.generateImage(umlString.toString(), new File(outPutFilePath + "\\generatedUML.PNG"));
        this.umlTextWriter.writeUMLText(outPutFilePath, umlString.toString());
    }

    private void appendParsedField(FieldAdapter fieldAdapter, StringBuilder classString, String className, Map<String, String> singleLetterMappings) {
        classString.append(fieldAdapter.getIsPublic() ? "+" : "-");
        String fieldType = getFieldType(singleLetterMappings, fieldAdapter);
        classString.append("{field} " + fieldType + " " + fieldAdapter.getFieldName() + "\n");
        hasAs.add(className + " -> " + fieldType);
    }

    private void appendParsedMethod(MethodAdapter methodAdapter, StringBuilder classString, String className, ArrayList<String> alreadyDependent) {
        classString.append(methodAdapter.getIsPublic() ? "+" : "-");
        determineMethodType(methodAdapter, classString, className);
        if (methodAdapter.getArgTypes().size() == 0){
            classString.append("):");
        }
        determineMethodArgs(methodAdapter, classString, className, alreadyDependent);
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

    private void determineMethodType(MethodAdapter methodAdapter, StringBuilder classString, String className) {
        if (methodAdapter.getMethodName().equals("<init>")){
            classString.append("{method} " + className + "(");
        }else if (methodAdapter.getIsAbstract()){
            classString.append("{abstract} " + methodAdapter.getMethodName() + "(");
        }else{
            classString.append("{method} " + methodAdapter.getMethodName() + "(");
        }
    }

    private void determineMethodArgs(MethodAdapter methodAdapter, StringBuilder classString, String className, ArrayList<String> alreadyDependent) {
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
    }

    private String getFieldType(Map<String, String> singleLetterMappings, FieldAdapter fieldAdapter) {
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
        return fieldType;
    }

    private void appendEnumClass(StringBuilder umlString, ClassAdapter classAdapter, StringBuilder classString) {
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

    private String getClassPrefix(ClassAdapter classAdapter, String className) {
        String classString;
        if (classAdapter.getIsInterface()) {
            classString = "interface " + className + "{\n";
        } else if (classAdapter.getIsAbstract()) {
            classString = "abstract " + className + "{\n";
        } else if (classAdapter.getIsEnum()) {
            classString = "Enum " + className + "{\n";
        } else {
            classString = "class " + className + "{\n";
        }
        return classString;
    }

    private void initLists() {
        hasAs = new ArrayList<>();
        isAs = new ArrayList<>();
        dependsOn = new ArrayList<>();
        classesInProject = new ArrayList<>();
    }

}
