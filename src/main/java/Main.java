import DataSource.*;
import Domain.*;
import Presentation.ConsoleUI;
import Presentation.UI;

public class Main {

    public static void main(String[] args) {
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(new SourceStringReaderCreator()), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManager, umlParser);
        UI ui = new ConsoleUI(linter);
        ui.UIMain();
    }
}
