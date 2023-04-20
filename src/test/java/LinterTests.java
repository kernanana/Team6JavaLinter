import DataSource.DefaultDataLoader;
import DataSource.PlantUMLSourceStringReader;
import DataSource.PrintWriterUMLTextWriter;
import Domain.*;
import Domain.Checks.Check;
import Domain.Checks.RedundantInterface;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LinterTests {

    @Test
    public void testLinterRedundantInterfaceCheckIntegration() {
        // Arrange
        ProjectDataManager projectDataManagerMock = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParserMock = new PlantClassUMLParser(new PlantUMLSourceStringReader(), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManagerMock, umlParserMock);

        List<CheckType> checkTypes = Arrays.asList(CheckType.RedundantInterface);
        Map<CheckType, Check> availableChecks = new HashMap<>();
        availableChecks.put(checkTypes.get(0), new RedundantInterface());
        linter.defineAvailableChecks(availableChecks);
        UserOptions userOptions = new UserOptions();
        List<PresentationInformation> results = new ArrayList<>();

        // Act
        try {
            results = linter.runChecks(checkTypes, "./src/test/resources/RedundantInterface", userOptions);
        } catch(IOException exception) {
            fail("Linter IOException encountered");
        }

        assertEquals(1, results.size());

        PresentationInformation result = results.get(0);
        assertFalse(result.hasPassed());
        assertEquals("Class ASMPracticeCode/RedundantInterface/RedundantInterface both inherits and implements method \"void methodE()\" from class ASMPracticeCode/RedundantInterface/ValidClass and interface ASMPracticeCode/RedundantInterface/TestInterfaceD", result.getDisplayLines().get(0));
    }

    @Test
    public void testLinterIOException() {

        // Arrange
        ProjectDataManager projectDataManagerMock = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParserMock = new PlantClassUMLParser(new PlantUMLSourceStringReader(), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(projectDataManagerMock, umlParserMock);

        List<CheckType> checkTypes = new ArrayList<>();
        UserOptions userOptions = new UserOptions();
        List<PresentationInformation> results = new ArrayList<>();

        // Act/Assert
        Throwable exception = assertThrows(IOException.class, () -> {
            linter.runChecks(checkTypes, "./src/test/resources/ThisPathDoesNotExist", userOptions);
        });

        assertTrue(exception.getMessage().startsWith("File or directory does not exist at path"));

    }

}