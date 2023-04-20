
import DataSource.*;
import Domain.*;
import Domain.Adapters.ClassAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

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

    @Test
    public void parsesPublicConcreteClassAndMethods() {
        String[] expectedDependencies = {"@startuml\n",
                "class PublicConcreteClassWithPublicMethods{\n+{method} PublicConcreteClassWithPublicMethods():void\n+{method} " +
                        "method1():void\n+{method} method2():boolean\n+{method} method3():String\n}\n", "@enduml\n"};
        verifyDependencies(expectedDependencies, "./src/test/resources/PUMLDummyData/PublicConcreteClassWithPublicMethods",
                "./src/test/resources/PUMLTest1GeneratedPUML");
    }

    @Test
    public void parsesAbstractMethodsAndClassesAndPrivateMethodsAndExtensions() {
        String[] expectedDependencies = {"@startuml\n",
                "abstract AbstractClassPrivateAbstractMethodsWithAbstractExtender{\n+{method} AbstractClassPrivateAbstractMethodsWithAbstractExtender():void\n+{abstract}" +
                        " publicAbstractMethod():void\n-{method} privateMethod1():boolean\n-{method} privateMethod2():String\n}\n",
                "class ExtendsAbstract{\n+{method} ExtendsAbstract():void\n+{method} publicAbstractMethod():void\n}\n",
                "AbstractClassPrivateAbstractMethodsWithAbstractExtender <|-- ExtendsAbstract\n",
                "@enduml\n"};
        verifyDependencies(expectedDependencies, "./src/test/resources/PUMLDummyData/AbstractMethodsAndClassAndPrivateMethodsAndExtension",
                "./src/test/resources/PUMLTest2GeneratedPUML");
    }

    @Test
    public void parsesInterfaceHasAEnumAndFields() {
        String[] expectedDependencies = {"@startuml\n", "Enum IAmAnEnum{\nIm,doing,enummy,stuff\n}\n",
                "interface IAmAnInterface{\n}\n", "class IImplementAndHave{\n-{field} IAmAnEnum myEnum\n+{method} IImplementAndHave():void\n}\n",
                "IAmAnInterface <|-- IImplementAndHave\n", "IImplementAndHave -> IAmAnEnum\n", "@enduml\n"};
        verifyDependencies(expectedDependencies, "./src/test/resources/PUMLDummyData/EnumHasAInterfaceAndFields",
                "./src/test/resources/PUMLTest3GeneratedPUML");
    }

    @Test
    public void parsesDependencies(){
        String[] expectedDependencies = {"@startuml\n", "class Dependency1{\n+{method} Dependency1():void\n}\n",
                "class Dependency2{\n+{method} Dependency2():void\n}\n",
                "class DependsOn{\n+{method} DependsOn():void\n-{method} dependsOn(Dependency1):void\n-{method} dependsOn2():Dependency2\n}\n",
                "DependsOn .> Dependency1\n", "DependsOn .> Dependency2\n", "@enduml\n"};
        verifyDependencies(expectedDependencies, "./src/test/resources/PUMLDummyData/PUMLDependant", "./src/test/resources/PUMLTest4GeneratedPUML"); // Can see output here if u want, can delete existing output and re-run too if u dont trusnt me
    }

}
