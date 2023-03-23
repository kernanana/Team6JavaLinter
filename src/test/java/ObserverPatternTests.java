import DataSource.DefaultDataLoader;
import Domain.*;
import Domain.Checks.Check;
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
        PresentationInformation result = observerPatternCheck.check(projectDataManager.generateClassAdapters("./src/test/resources/ObserverPatternDummyData/ValidObserverPattern"), new UserOptions());
        String expected = "Observer Pattern has been detected: \n"
                + "\t Subject: Subject | Observer: Observer\n"
                + "\t Concrete Subject: ConcreteSubject | Concrete Observer: ConcreteObserver";
        Assertions.assertTrue(result.passed);
        Assertions.assertEquals(expected, result.displayLines.get(0));
    }

    @Test
    public void testCheckForInvalidObserverPattern() {
        Check observerPatternCheck = new ObserverPatternCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = observerPatternCheck.check(projectDataManager.generateClassAdapters("./src/test/resources/ObserverPatternDummyData/InvalidObserverPattern"), new UserOptions());

        Assertions.assertFalse(result.passed);
        Assertions.assertTrue(result.displayLines.isEmpty());
    }
}