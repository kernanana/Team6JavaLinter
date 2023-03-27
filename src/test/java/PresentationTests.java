import Domain.CheckType;
import Domain.PresentationInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PresentationTests {

    @Test void TestReinitialize(){
        PresentationInformation pi = new PresentationInformation();
        pi.reinitialize(CheckType.DecoratorPattern);
        Assertions.assertFalse(pi.passed);
        Assertions.assertEquals(pi.checkName, CheckType.DecoratorPattern);
        Assertions.assertEquals(pi.displayLines, new ArrayList<>());
    }

    @Test
    public void TestReset(){
        PresentationInformation pi = new PresentationInformation();
        pi.checkName = CheckType.valueOf("PoorNamingConvention");
        pi.resetPI();
        Assertions.assertFalse(pi.passed);
        Assertions.assertEquals(pi.checkName, CheckType.PoorNamingConvention);
        Assertions.assertEquals(pi.displayLines, new ArrayList<>());
    }

    @Test
    public void TestCountLines(){
        PresentationInformation pi = new PresentationInformation();
        pi.reinitialize(CheckType.DecoratorPattern);
        Assertions.assertFalse(pi.passed);
        Assertions.assertEquals(pi.checkName, CheckType.DecoratorPattern);
        Assertions.assertEquals(pi.displayLines, new ArrayList<>());
        Assertions.assertEquals(pi.countDisplayLines(), 0);
        pi.displayLines.add("Sample display Line");
        Assertions.assertEquals(pi.countDisplayLines(), 1);
    }

    @Test void TestIsPresentingCheck(){
        PresentationInformation pi = new PresentationInformation();
        pi.reinitialize(CheckType.DecoratorPattern);
        Assertions.assertFalse(pi.passed);
        Assertions.assertEquals(pi.checkName, CheckType.DecoratorPattern);
        Assertions.assertEquals(pi.displayLines, new ArrayList<>());
        Assertions.assertTrue(pi.isPresentingCheck(CheckType.DecoratorPattern));
    }

    @Test void TestreturnUIMessage(){
        PresentationInformation pi = new PresentationInformation();
        pi.reinitialize(CheckType.DecoratorPattern);
        Assertions.assertFalse(pi.passed);
        Assertions.assertEquals(pi.checkName, CheckType.DecoratorPattern);
        Assertions.assertEquals(pi.displayLines, new ArrayList<>());
        Assertions.assertEquals(pi.returnUIMessage(), "DecoratorPattern failed!");
        pi.passed = true;
        Assertions.assertEquals(pi.returnUIMessage(), "DecoratorPattern passed!");
    }
}
