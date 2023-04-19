
import DataSource.*;
import Domain.*;
import Domain.Adapters.ClassAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class UMLParserTest {
    @Test
    public void parsesPublicConcreteClassAndMethods(){
        SourceStringReaderAdapter sourceStringReaderAdapter = new PlantUMLSourceStringReader();
        PlantClassUMLParser parserUndertest = new PlantClassUMLParser(sourceStringReaderAdapter, new PrintWriterUMLTextWriter());
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        ArrayList<ClassAdapter> classAdapters = (ArrayList<ClassAdapter>) projectDataManager.generateClassAdapters("./src/test/resources/PUMLDummyData/PublicConcreteClassWithPublicMethods");
        String result = parserUndertest.parseUML(classAdapters, "./src/test/resources/PUMLTest1GeneratedPUML"); // Can see output here if u want, can delete existing output and re-run too if u dont trusnt me
        Assertions.assertTrue(result.contains("@startuml\n"));
        Assertions.assertTrue(result.contains("class PublicConcreteClassWithPublicMethods{\n" +
                "+{method} PublicConcreteClassWithPublicMethods():void\n" +
                "+{method} method1():void\n" +
                "+{method} method2():boolean\n" +
                "+{method} method3():String\n" +
                "}\n"));
        Assertions.assertTrue(result.contains("@enduml\n"));
    }

    @Test
    public void parsesAbstractMethodsAndClassesAndPrivateMethodsAndExtensions(){
        SourceStringReaderAdapter sourceStringReaderAdapter = new PlantUMLSourceStringReader();
        PlantClassUMLParser parserUndertest = new PlantClassUMLParser(sourceStringReaderAdapter, new PrintWriterUMLTextWriter());
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        ArrayList<ClassAdapter> classAdapters = (ArrayList<ClassAdapter>) projectDataManager.generateClassAdapters("./src/test/resources/PUMLDummyData/AbstractMethodsAndClassAndPrivateMethodsAndExtension");
        String result = parserUndertest.parseUML(classAdapters, "./src/test/resources/PUMLTest2GeneratedPUML"); // Can see output here if u want, can delete existing output and re-run too if u dont trusnt me
        Assertions.assertTrue(result.contains("@startuml\n"));
        Assertions.assertTrue(result.contains("abstract AbstractClassPrivateAbstractMethodsWithAbstractExtender{\n" +
                "+{method} AbstractClassPrivateAbstractMethodsWithAbstractExtender():void\n" +
                "+{abstract} publicAbstractMethod():void\n" +
                "-{method} privateMethod1():boolean\n" +
                "-{method} privateMethod2():String\n" +
                "}\n"));
        Assertions.assertTrue(result.contains("class ExtendsAbstract{\n" +
                "+{method} ExtendsAbstract():void\n" + //constructor
                "+{method} publicAbstractMethod():void\n" +
                "}\n"));
        Assertions.assertTrue(result.contains("AbstractClassPrivateAbstractMethodsWithAbstractExtender <|-- ExtendsAbstract\n"));
        Assertions.assertTrue(result.contains("@enduml\n"));
    }

    @Test
    public void parsesInterfaceHasAEnumAndFields(){
        SourceStringReaderAdapter sourceStringReaderAdapter = new PlantUMLSourceStringReader();
        PlantClassUMLParser parserUndertest = new PlantClassUMLParser(sourceStringReaderAdapter, new PrintWriterUMLTextWriter());
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        ArrayList<ClassAdapter> classAdapters = (ArrayList<ClassAdapter>) projectDataManager.generateClassAdapters("./src/test/resources/PUMLDummyData/EnumHasAInterfaceAndFields");
        String result = parserUndertest.parseUML(classAdapters, "./src/test/resources/PUMLTest3GeneratedPUML"); // Can see output here if u want, can delete existing output and re-run too if u dont trusnt me
        Assertions.assertTrue(result.contains("@startuml\n"));
        Assertions.assertTrue(result.contains("Enum IAmAnEnum{\n" +
                "Im,doing,enummy,stuff\n" +
                "}\n"));
        Assertions.assertTrue(result.contains("interface IAmAnInterface{\n" +
                "}\n"));
        Assertions.assertTrue(result.contains("class IImplementAndHave{\n" +
                "-{field} IAmAnEnum myEnum\n" +
                "+{method} IImplementAndHave():void\n" +
                "}\n"));
        Assertions.assertTrue(result.contains("IAmAnInterface <|-- IImplementAndHave\n"));
        Assertions.assertTrue(result.contains("IImplementAndHave -> IAmAnEnum\n"));
        Assertions.assertTrue(result.contains("@enduml\n"));
    }

    @Test
    public void parsesDependencies(){
        SourceStringReaderAdapter sourceStringReaderAdapter = new PlantUMLSourceStringReader();
        PlantClassUMLParser parserUndertest = new PlantClassUMLParser(sourceStringReaderAdapter, new PrintWriterUMLTextWriter());
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        ArrayList<ClassAdapter> classAdapters = (ArrayList<ClassAdapter>) projectDataManager.generateClassAdapters("./src/test/resources/PUMLDummyData/PUMLDependant");
        String result = parserUndertest.parseUML(classAdapters, "./src/test/resources/PUMLTest4GeneratedPUML"); // Can see output here if u want, can delete existing output and re-run too if u dont trusnt me
        System.out.println(result);
        Assertions.assertTrue(result.contains("@startuml\n"));
        Assertions.assertTrue(result.contains("class Dependency1{\n" +
                "+{method} Dependency1():void\n" +
                "}\n"));
        Assertions.assertTrue(result.contains("class Dependency2{\n" +
                "+{method} Dependency2():void\n" +
                "}\n"));
        Assertions.assertTrue(result.contains("class DependsOn{\n" +
                "+{method} DependsOn():void\n" +
                "-{method} dependsOn(Dependency1):void\n" + //Catches args
                "-{method} dependsOn2():Dependency2\n" + //Catches return type
                "}\n"));
        Assertions.assertTrue(result.contains("DependsOn .> Dependency1\n"));
        Assertions.assertTrue(result.contains("DependsOn .> Dependency2\n"));
        Assertions.assertTrue(result.contains("@enduml\n"));
     }

}
