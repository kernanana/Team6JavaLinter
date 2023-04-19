import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Adapters.ClassAdapter;
import Domain.CheckType;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.StrategyPatternCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StrategyPatternTests {

    @Test
    public void testCreatesAdapter() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/StrategyDummyData/StrategyInterface.class");
        Assertions.assertEquals("ASMPracticeCode/StrategyDummyData/StrategyInterface", adapter.getClassName());
    }

    @Test
    public void testDetectsPattern() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/StrategyDummyData/StrategyInterface.class");
        ClassAdapter adapter2 = manager.createClassAdapter("./src/test/resources/StrategyDummyData/ConcreteStrategy.class");
        ClassAdapter adapter3 = manager.createClassAdapter("./src/test/resources/StrategyDummyData/ConcreteStrategy1.class");
        List<ClassAdapter> adapterList = new ArrayList<>();
        adapterList.add(adapter);
        adapterList.add(adapter2);
        adapterList.add(adapter3);
        Check check = new StrategyPatternCheck();
        CheckData checkData = new CheckData(adapterList, null);
        PresentationInformation info = check.check(checkData);
        Assertions.assertTrue(info.hasPassed());
        Assertions.assertEquals(CheckType.StrategyPattern, info.getCheckName());
        String str2 = "Strategy Abstraction Class: ASMPracticeCode/StrategyDummyData/StrategyInterface, Concrete Strategy Classes: ASMPracticeCode/StrategyDummyData/ConcreteStrategy, ASMPracticeCode/StrategyDummyData/ConcreteStrategy1";
        Assertions.assertEquals(str2, info.getDisplayLines().get(0));
    }

    @Test
    public void testDetectsNoPattern() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/HollywoodPrincipleDummyData/Observer.class");
        ClassAdapter adapter2 = manager.createClassAdapter("./src/test/resources/HollywoodPrincipleDummyData/Observee.class");
        List<ClassAdapter> adapterList = new ArrayList<>();
        adapterList.add(adapter);
        adapterList.add(adapter2);
        Check check = new StrategyPatternCheck();
        CheckData checkData = new CheckData(adapterList, null);
        PresentationInformation info = check.check(checkData);
        Assertions.assertFalse(info.hasPassed());
        Assertions.assertEquals(CheckType.StrategyPattern, info.getCheckName());
        Assertions.assertEquals(0, info.getDisplayLines().size());
    }
}
