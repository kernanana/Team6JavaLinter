import Domain.CheckType;
import Domain.PresentationInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PresentationTests {

    @Test void TestReinitialize(){
        PresentationInformation pi = new PresentationInformation(CheckType.DecoratorPattern);
        pi.reinitialize(CheckType.DecoratorPattern);
        Assertions.assertFalse(pi.hasPassed());
        Assertions.assertEquals(pi.getCheckName(), CheckType.DecoratorPattern);
        Assertions.assertEquals(pi.getDisplayLines(), new ArrayList<>());
    }

    @Test
    public void TestReset(){
        PresentationInformation pi = new PresentationInformation(CheckType.PoorNamingConvention);
        pi.resetPI();
        Assertions.assertFalse(pi.hasPassed());
        Assertions.assertEquals(pi.getCheckName(), CheckType.PoorNamingConvention);
        Assertions.assertEquals(pi.getDisplayLines(), new ArrayList<>());
    }

    @Test
    public void TestCountLines(){
        PresentationInformation pi = new PresentationInformation(CheckType.DecoratorPattern);
        pi.reinitialize(CheckType.DecoratorPattern);
        Assertions.assertFalse(pi.hasPassed());
        Assertions.assertEquals(pi.countDisplayLines(), 0);
        pi.addDisplayLine("Sample display Line");
        Assertions.assertEquals(pi.countDisplayLines(), 1);
    }

    @Test void TestIsPresentingCheck(){
        PresentationInformation pi = new PresentationInformation(CheckType.DecoratorPattern);
        pi.reinitialize(CheckType.DecoratorPattern);
        Assertions.assertFalse(pi.hasPassed());
        Assertions.assertTrue(pi.isPresentingCheck(CheckType.DecoratorPattern));
    }

    @Test void TestReturnUIMessage(){
        PresentationInformation pi = new PresentationInformation(CheckType.DecoratorPattern);
        pi.reinitialize(CheckType.DecoratorPattern);
        Assertions.assertFalse(pi.hasPassed());
        Assertions.assertEquals(pi.returnUIMessage(), "DecoratorPattern failed!");
        pi.passedCheck();
        Assertions.assertEquals(pi.returnUIMessage(), "DecoratorPattern passed!");
    }
}
