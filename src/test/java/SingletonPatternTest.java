import ASMPracticeCode.SingletonPatternDummyData.EagerSingleton;
import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.InformationHidingCheck;
import Domain.Checks.SingletonPatternCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SingletonPatternTest {

    @Test
    public void noSingletonExists(){
        Check check = new SingletonPatternCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = check.check(projectDataManager.generateClassAdapters("src/test/resources/SingletonPatternDummyData/NotSingleton"), new UserOptions());
        Assertions.assertFalse(result.passed); //false if no Singleton detected
        Assertions.assertTrue(result.displayLines.size() == 0);
    }
    @Test
    public void EagerSingletonTest(){
        Check check = new SingletonPatternCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = check.check(projectDataManager.generateClassAdapters("src/test/resources/SingletonPatternDummyData/EagerSingleton"), new UserOptions());
        Assertions.assertTrue(result.passed); //false if no Singleton detected
        Assertions.assertTrue(result.displayLines.size() == 1);
        Assertions.assertEquals(result.displayLines.get(0), "Singleton Pattern detected for ASMPracticeCode/SingletonPatternDummyData/EagerSingleton");
    }

    @Test
    public void LazySingletonTest(){
        Check check = new SingletonPatternCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = check.check(projectDataManager.generateClassAdapters("src/test/resources/SingletonPatternDummyData/LazySingleton"), new UserOptions());
        Assertions.assertTrue(result.passed); //false if no Singleton detected
        Assertions.assertTrue(result.displayLines.size() == 1);
        Assertions.assertEquals(result.displayLines.get(0), "Singleton Pattern detected for ASMPracticeCode/SingletonPatternDummyData/LazySingleton");
    }
}
