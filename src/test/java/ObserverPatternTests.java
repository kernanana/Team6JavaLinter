import DataSource.DefaultDataLoader;
import Domain.*;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.MockCheckData;
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
        MockCheckData checkData = new MockCheckData("./src/test/resources/ObserverPatternDummyData/ValidObserverPattern");
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
        MockCheckData checkData = new MockCheckData("./src/test/resources/ObserverPatternDummyData/InValidObserverPattern");
        PresentationInformation result = observerPatternCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed());
        Assertions.assertTrue(result.getDisplayLines().isEmpty());
    }
}