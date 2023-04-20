import DataSource.ConfigParser;
import DataSource.DefaultDataLoader;
import DataSource.PlantUMLSourceStringReader;
import DataSource.PrintWriterUMLTextWriter;
import Domain.*;
import Presentation.ConsoleUI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RunAllChecksTests {
    @Test
    public void TestRunAllChecks_WithCurrentState(){
        String configPath = "./src/test/resources/test-config.yaml";
        Map<String, Object> config = new ConfigParser().parseConfig(configPath);
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManager, umlParser);
        ConsoleUI cui = new ConsoleUI(linter, config);
        List<Integer> expected = new ArrayList<>();
        for(int i = 0; i<cui.availableChecks.size();i++){
            expected.add(i);
        }
        List<Integer> actual = cui.fillWithAllChecks();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void TestRunAllChecks_IfNoChecksExist(){
        String configPath = "./src/test/resources/test-config.yaml";
        Map<String, Object> config = new ConfigParser().parseConfig(configPath);
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManager, umlParser);
        ConsoleUI cui = new ConsoleUI(linter, config);
        List<Integer> expected = new ArrayList<>();
        cui.availableChecks = new ArrayList<>();
        List<Integer> actual = cui.fillWithAllChecks();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void TestRunAllChecks_With12Checks(){
        String configPath = "./src/test/resources/test-config.yaml";
        Map<String, Object> config = new ConfigParser().parseConfig(configPath);
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManager, umlParser);
        ConsoleUI cui = new ConsoleUI(linter, config);
        List<CheckType> checks = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        for(int i = 0; i<12;i++){
            expected.add(i);
            checks.add(CheckType.NoFinalizerCheck);
        }
        cui.availableChecks = checks;
        List<Integer> actual = cui.fillWithAllChecks();
        Assertions.assertEquals(expected,actual);
    }
}
