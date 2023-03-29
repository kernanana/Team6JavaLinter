import DataSource.DefaultDataLoader;
import Domain.*;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.ObserverPatternCheck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObserverPatternTests {

    @Test
    public void testCreateObserverPatternDetector() {
        Check observerPatternCheck = new ObserverPatternCheck();
        Assertions.assertNotNull(observerPatternCheck);
    }

    @Test
    public void testCheckForObserverPattern() {
        Check observerPatternCheck = new ObserverPatternCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/ObserverPatternDummyData/ValidObserverPattern"), new UserOptions());
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
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/ObserverPatternDummyData/InvalidObserverPattern"), new UserOptions());
        PresentationInformation result = observerPatternCheck.check(checkData);

        Assertions.assertFalse(result.hasPassed());
        Assertions.assertTrue(result.getDisplayLines().isEmpty());
    }
}