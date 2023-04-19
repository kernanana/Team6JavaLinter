import ASMPracticeCode.SingletonPatternDummyData.EagerSingleton;
import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.InformationHidingCheck;
import Domain.Checks.SingletonPatternCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SingletonPatternTest {
    private CheckData setUpCheckData(String filepath) {
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        return new CheckData(projectDataManager.generateClassAdapters(filepath), new UserOptions());
    }

    @Test
    public void noSingletonExists(){
        Check check = new SingletonPatternCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/SingletonPatternDummyData/NotSingleton");
        PresentationInformation result = check.check(checkData);
        Assertions.assertFalse(result.hasPassed()); //false if no Singleton detected
        Assertions.assertTrue(result.getDisplayLines().size() == 0);
    }
    @Test
    public void EagerSingletonTest(){
        Check check = new SingletonPatternCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/SingletonPatternDummyData/EagerSingleton");
        PresentationInformation result = check.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //false if no Singleton detected
        Assertions.assertTrue(result.countDisplayLines() == 1);
        Assertions.assertEquals(result.getDisplayLines().get(0), "Singleton Pattern detected for ASMPracticeCode/SingletonPatternDummyData/EagerSingleton");
    }

    @Test
    public void LazySingletonTest(){
        Check check = new SingletonPatternCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/SingletonPatternDummyData/LazySingleton");
        PresentationInformation result = check.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //false if no Singleton detected
        Assertions.assertTrue(result.countDisplayLines() == 1);
        Assertions.assertEquals(result.getDisplayLines().get(0), "Singleton Pattern detected for ASMPracticeCode/SingletonPatternDummyData/LazySingleton");
    }
}
