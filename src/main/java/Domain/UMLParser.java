package Domain;

import Domain.Adapters.ClassAdapter;

import java.util.List;

public abstract class UMLParser {
    abstract public String parseUML(List<ClassAdapter> classes, String outPutFilePath);
}
