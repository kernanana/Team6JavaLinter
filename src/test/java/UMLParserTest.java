
import DataSource.*;
import Domain.*;
import Domain.Adapters.ClassAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class UMLParserTest {

    private void verifyDependencies(String[] expectedDependencies, String dummyDataPath, String generatedPUMLPath) {
        SourceStringReaderAdapter sourceStringReaderAdapter = new PlantUMLSourceStringReader();
        PlantClassUMLParser parserUndertest = new PlantClassUMLParser(sourceStringReaderAdapter, new PrintWriterUMLTextWriter());
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        ArrayList<ClassAdapter> classAdapters = (ArrayList<ClassAdapter>) projectDataManager.generateClassAdapters(dummyDataPath);
        String result = parserUndertest.parseUML(classAdapters, generatedPUMLPath);

        for (String dependency : expectedDependencies) {
            Assertions.assertTrue(result.contains(dependency));
        }
    }

    private String[] readDependenciesFromFile(String filename) throws IOException {
        File file = new File(filename);
        List<String> lines = Files.readAllLines(file.toPath());
        return lines.toArray(new String[0]);
    }

    @Test
    public void parsesPublicConcreteClassAndMethods() throws IOException {
        String[] expectedDependencies = readDependenciesFromFile("./src/test/resources/DependencyExamples/PublicConcreteClassWithPublicMethods.txt");
        verifyDependencies(expectedDependencies, "./src/test/resources/PUMLDummyData/PublicConcreteClassWithPublicMethods",
                "./src/test/resources/PUMLTest1GeneratedPUML");
    }

    @Test
    public void parsesAbstractMethodsAndClassesAndPrivateMethodsAndExtensions() throws IOException {
        String[] expectedDependencies = readDependenciesFromFile("./src/test/resources/DependencyExamples/AbstractClassPrivateAbstractMethodsWithAbstractExtender.txt");
        verifyDependencies(expectedDependencies, "./src/test/resources/PUMLDummyData/AbstractMethodsAndClassAndPrivateMethodsAndExtension",
                "./src/test/resources/PUMLTest2GeneratedPUML");
    }

    @Test
    public void parsesInterfaceHasAEnumAndFields() throws IOException {
        String[] expectedDependencies = readDependenciesFromFile("./src/test/resources/DependencyExamples/IAmAnEnum.txt");
        verifyDependencies(expectedDependencies, "./src/test/resources/PUMLDummyData/EnumHasAInterfaceAndFields",
                "./src/test/resources/PUMLTest3GeneratedPUML");
    }

    @Test
    public void parsesDependencies() throws IOException {
        String[] expectedDependencies = readDependenciesFromFile("./src/test/resources/DependencyExamples/Dependency1.txt");
        verifyDependencies(expectedDependencies, "./src/test/resources/PUMLDummyData/PUMLDependant", "./src/test/resources/PUMLTest4GeneratedPUML"); // Can see output here if u want, can delete existing output and re-run too if u dont trusnt me
    }

}
