import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.DecoratorPatternCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DecoratorPatternTest {
    private CheckData setUpCheckData(String filepath) {
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        return new CheckData(projectDataManager.generateClassAdapters(filepath), new UserOptions());
    }

    @Test
    public void hasPattern(){
        Check decoratorCheck = new DecoratorPatternCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/DecoratorPatternDummyData/DecoratorPatternHasPattern");
        PresentationInformation result = decoratorCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); // Says that pattern was detected
        Assertions.assertEquals("ASMPracticeCode/DecoratorPatternHasPattern/Decorator decorates ASMPracticeCode/DecoratorPatternHasPattern/Decorated", result.getDisplayLines().get(0)); //displays correct informaiton
    }

    @Test
    public void doesntExtend(){
        Check decoratorCheck = new DecoratorPatternCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/DecoratorPatternDummyData/DecoratorPatternDoesntExtend");
        PresentationInformation result = decoratorCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed()); // Says that pattern was not detected
        Assertions.assertEquals(0, result.countDisplayLines()); //no information because the pattern was never found
    }

    @Test
    public void doesntHaveFieldInstance(){
        Check decoratorCheck = new DecoratorPatternCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/DecoratorPatternDummyData/DecoratorPatternDoesntHaveInstance");
        PresentationInformation result = decoratorCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed()); // Says that pattern was not detected
        Assertions.assertEquals(0, result.countDisplayLines()); //no information because the pattern was never found
    }
}
