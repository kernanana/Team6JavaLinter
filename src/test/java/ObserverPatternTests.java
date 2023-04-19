import DataSource.DefaultDataLoader;
import Domain.*;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.ObserverPatternCheck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObserverPatternTests {
    private CheckData setUpCheckData(String filepath) {
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        return new CheckData(projectDataManager.generateClassAdapters(filepath), new UserOptions());
    }

    @Test
    public void testCreateObserverPatternDetector() {
        Check observerPatternCheck = new ObserverPatternCheck();
        Assertions.assertNotNull(observerPatternCheck);
    }

    @Test
    public void testCheckForObserverPattern() {
        Check observerPatternCheck = new ObserverPatternCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/ObserverPatternDummyData/ValidObserverPattern");
        PresentationInformation result = observerPatternCheck.check(checkData);
        String expected = "Observer Pattern has been detected: \n"
                + "\t Subject: Subject | Observer: Observer\n"
                + "\t Concrete Subject: ConcreteSubject | Concrete Observer: ConcreteObserver";
        Assertions.assertTrue(result.hasPassed());
        Assertions.assertEquals(expected, result.getDisplayLines().get(0));
    }

    @Test
    public void testCheckForInvalidObserverPattern() {
        Check observerPatternCheck = new ObserverPatternCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/ObserverPatternDummyData/InvalidObserverPattern");
        PresentationInformation result = observerPatternCheck.check(checkData);

        Assertions.assertFalse(result.hasPassed());
        Assertions.assertTrue(result.getDisplayLines().isEmpty());
    }
}