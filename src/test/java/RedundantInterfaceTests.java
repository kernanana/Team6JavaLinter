import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.DecoratorPatternCheck;
import Domain.Checks.RedundantInterface;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RedundantInterfaceTests {

    @Test
    public void testCheckRedundantInterfaceFail() {
        Check redundantInterfaceCheck = new RedundantInterface();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = redundantInterfaceCheck.check(projectDataManager.generateClassAdapters("./src/test/resources/RedundantInterface"), new UserOptions());
        Assertions.assertFalse(result.passed); // Says that pattern was detected
        Assertions.assertEquals("Class ASMPracticeCode/RedundantInterface/RedundantInterface both inherits and implements method \"void methodE()\" from class ASMPracticeCode/RedundantInterface/ValidClass and interface ASMPracticeCode/RedundantInterface/TestInterfaceD", result.displayLines.get(0)); //displays correct informaiton
    }

    @Test
    public void testCheckRedundantInterfacePass(){
        Check redundantInterfaceCheck = new RedundantInterface();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = redundantInterfaceCheck.check(projectDataManager.generateClassAdapters("./src/test/resources/DecoratorPatternDummyData/DecoratorPatternHasPattern"), new UserOptions());
        Assertions.assertTrue(result.passed); // Says that pattern was detected
    }

}
