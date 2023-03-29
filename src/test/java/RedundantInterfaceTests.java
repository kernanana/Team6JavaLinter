import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
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
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/RedundantInterface"), new UserOptions());
        PresentationInformation result = redundantInterfaceCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed()); // Says that pattern was detected
        Assertions.assertEquals("Class ASMPracticeCode/RedundantInterface/RedundantInterface both inherits and implements method \"void methodE()\" from class ASMPracticeCode/RedundantInterface/ValidClass and interface ASMPracticeCode/RedundantInterface/TestInterfaceD", result.getDisplayLines().get(0)); //displays correct informaiton
    }

    @Test
    public void testCheckRedundantInterfacePass(){
        Check redundantInterfaceCheck = new RedundantInterface();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/DecoratorPatternDoesntExtend"), new UserOptions());
        PresentationInformation result = redundantInterfaceCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); // Says that pattern was detected
    }

}
