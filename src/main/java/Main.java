import DataSource.*;
import Domain.*;
import Presentation.ConsoleUI;

public class Main {

    private static final String CONFIG_PATH = "./config.yaml";
    private static final Map<String, Object> config = new ConfigParser().parseConfig(CONFIG_PATH);

    public static void main(String[] args) {
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(new SourceStringReaderCreator()), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManager, umlParser);
        ConsoleUI ui = new ConsoleUI(linter, availableChecks, config);
        ui.UIMain();
    }
}
