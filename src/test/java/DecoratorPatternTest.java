import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.DecoratorPatternCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DecoratorPatternTest {
    @Test
    public void hasPattern(){
        Check decoratorCheck = new DecoratorPatternCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = decoratorCheck.check(projectDataManager.generateClassAdapters("src/test/resources/DecoratorPatternDummyData/DecoratorPatternHasPattern"), new UserOptions());
        Assertions.assertTrue(result.passed); // Says that pattern was detected
        Assertions.assertEquals("ASMPracticeCode/DecoratorPatternHasPattern/Decorator decorates ASMPracticeCode/DecoratorPatternHasPattern/Decorated", result.displayLines.get(0)); //displays correct informaiton
    }

    @Test
    public void doesntExtend(){
        Check decoratorCheck = new DecoratorPatternCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = decoratorCheck.check(projectDataManager.generateClassAdapters("src/test/resources/DecoratorPatternDummyData/DecoratorPatternDoesntExtend"), new UserOptions());
        Assertions.assertFalse(result.passed); // Says that pattern was not detected
        Assertions.assertEquals(0, result.displayLines.size()); //no information because the pattern was never found
    }

    @Test
    public void doesntHaveFieldInstance(){
        Check decoratorCheck = new DecoratorPatternCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = decoratorCheck.check(projectDataManager.generateClassAdapters("src/test/resources/DecoratorPatternDummyData/DecoratorPatternDoesntHaveInstance"), new UserOptions());
        Assertions.assertFalse(result.passed); // Says that pattern was not detected
        Assertions.assertEquals(0, result.displayLines.size()); //no information because the pattern was never found
    }
}
