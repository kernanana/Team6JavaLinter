import DataSource.*;
import Domain.*;
import Presentation.ConsoleUI;
import Presentation.UI;
import java.util.Map;

public class Main {

    private static final String CONFIG_PATH = "./config.yaml";
    private static final Map<String, Object> config = new ConfigParser().parseConfig(CONFIG_PATH);

    public static void main(String[] args) {
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManager, umlParser);
        UI ui = new ConsoleUI(linter, config);
        ui.UIMain();
    }

}
