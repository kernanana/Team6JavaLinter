import DataSource.*;
import Domain.*;
import Presentation.ConsoleUI;
import Presentation.GUI;
import Presentation.UI;

import javax.swing.*;
import java.util.Map;

public class Main {

    private static final String CONFIG_PATH = "./config.yaml";
    private static final Map<String, Object> config = new ConfigParser().parseConfig(CONFIG_PATH);

    public static void main(String[] args) {
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManager, umlParser);
        String[] options = {"Console", "GUI"};
        int uiResult = JOptionPane.showOptionDialog(null, "Which form of UI would you like to use?",
                "Select UI",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        UI ui;
        if(uiResult == 0)
            ui = new ConsoleUI(linter, config);
        else
            ui = new GUI(linter, config);

        ui.UIMain();
    }

}
