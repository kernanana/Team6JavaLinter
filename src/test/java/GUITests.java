import DataSource.*;
import Domain.ASMProjectDataManager;
import Domain.Linter;
import Domain.PlantClassUMLParser;
import Domain.ProjectDataManager;
import Presentation.GUI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Map;

public class GUITests {

    @Test
    public void testSetUpGUIFromConfigFile() {
        String configPath = "./src/test/resources/test-config.yaml";
        Map<String, Object> config = new ConfigParser().parseConfig(configPath);
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManager, umlParser);
        GUI gui = new GUI(linter, config);
        gui.createChecksForUI(new JPanel());
        gui.setChecksFromConfig();
        Assertions.assertEquals(".\\src\\test\\resources\\DataClassDummyData", gui.getDirectoryTextField());
        for(JCheckBox checkBox : gui.getCheckBoxes()) {
            String checkBoxName = checkBox.getText();
            if(checkBoxName.equals("PoorNamingConvention") || checkBoxName.equals("EqualsHashCode") || checkBoxName.equals("SingleResponsibilityPrinciple"))
                Assertions.assertTrue(checkBox.isSelected());
            else
                Assertions.assertFalse(checkBox.isSelected());
        }
    }

    @Test
    public void testSelectAllChecksButton() {
        String configPath = "./src/test/resources/test-config.yaml";
        Map<String, Object> config = new ConfigParser().parseConfig(configPath);
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManager, umlParser);
        GUI gui = new GUI(linter, config);
        gui.createChecksForUI(new JPanel());
        gui.createButtonPanel();
        JButton selectAll = gui.getSelectAllButton();
        selectAll.doClick();

        for(JCheckBox checkBox : gui.getCheckBoxes())
            Assertions.assertTrue(checkBox.isSelected());
    }

    @Test
    public void testConfirmChecksButton() {
        String configPath = "./src/test/resources/test-config.yaml";
        Map<String, Object> config = new ConfigParser().parseConfig(configPath);
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManager, umlParser);
        GUI gui = new GUI(linter, config);
        gui.createChecksForUI(new JPanel());
        gui.createButtonPanel();
        JButton selectAll = gui.getSelectAllButton();
        selectAll.doClick();
        JButton confirmChecks = gui.getConfirmChecksButton();
        Assertions.assertTrue(gui.getSelectedChecks().isEmpty());
        confirmChecks.doClick();
        Assertions.assertEquals(gui.getCheckBoxes().size(), gui.getSelectedChecks().size());
    }
}
