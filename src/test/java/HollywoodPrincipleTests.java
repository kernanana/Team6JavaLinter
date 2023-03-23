import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Adapters.ClassAdapter;
import Domain.CheckType;
import Domain.Checks.Check;
import Domain.Checks.HollywoodPrincipleCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class HollywoodPrincipleTests {
    @Test
    public void testDetectsPattern() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/HollywoodPrincipleDummyData/HighLevelComponent.class");
        ClassAdapter adapter2 = manager.createClassAdapter("./src/test/resources/HollywoodPrincipleDummyData/LowLevelComponent.class");
        List<ClassAdapter> adapterList = new ArrayList<>();
        adapterList.add(adapter);
        adapterList.add(adapter2);
        Check check = new HollywoodPrincipleCheck();
        PresentationInformation info = check.check(adapterList, null);
        Assertions.assertFalse(info.passed);
        Assertions.assertEquals(CheckType.HollywoodPrinciple, info.checkName);
        String str = "High level class: ASMPracticeCode/HollywoodPrincipleDummyData/HighLevelComponent is called by low level class: ASMPracticeCode/HollywoodPrincipleDummyData/LowLevelComponent";
        Assertions.assertEquals(str, info.displayLines.get(0));
    }
    @Test
    public void testDetectsPatternObserver() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/HollywoodPrincipleDummyData/Observer.class");
        ClassAdapter adapter2 = manager.createClassAdapter("./src/test/resources/HollywoodPrincipleDummyData/Observee.class");
        List<ClassAdapter> adapterList = new ArrayList<>();
        adapterList.add(adapter);
        adapterList.add(adapter2);
        Check check = new HollywoodPrincipleCheck();
        PresentationInformation info = check.check(adapterList, null);
        Assertions.assertFalse(info.passed);
        Assertions.assertEquals(CheckType.HollywoodPrinciple, info.checkName);
        String str = "High level class: ASMPracticeCode/HollywoodPrincipleDummyData/Observer is called by low level class: ASMPracticeCode/HollywoodPrincipleDummyData/Observee";
        Assertions.assertEquals(str, info.displayLines.get(0));
    }

    @Test
    public void testDetectsMultiplePattern() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/HollywoodPrincipleDummyData/HighLevelComponent.class");
        ClassAdapter adapter2 = manager.createClassAdapter("./src/test/resources/HollywoodPrincipleDummyData/LowLevelComponent.class");
        ClassAdapter adapter3 = manager.createClassAdapter("./src/test/resources/HollywoodPrincipleDummyData/Observer.class");
        ClassAdapter adapter4 = manager.createClassAdapter("./src/test/resources/HollywoodPrincipleDummyData/Observee.class");
        List<ClassAdapter> adapterList = new ArrayList<>();
        adapterList.add(adapter);
        adapterList.add(adapter2);
        adapterList.add(adapter3);
        adapterList.add(adapter4);
        Check check = new HollywoodPrincipleCheck();
        PresentationInformation info = check.check(adapterList, null);
        Assertions.assertFalse(info.passed);
        Assertions.assertEquals(CheckType.HollywoodPrinciple, info.checkName);
        String str = "High level class: ASMPracticeCode/HollywoodPrincipleDummyData/HighLevelComponent is called by low level class: ASMPracticeCode/HollywoodPrincipleDummyData/LowLevelComponent";
        Assertions.assertEquals(str, info.displayLines.get(0));
        String str2 = "High level class: ASMPracticeCode/HollywoodPrincipleDummyData/Observer is called by low level class: ASMPracticeCode/HollywoodPrincipleDummyData/Observee";
        Assertions.assertEquals(str2, info.displayLines.get(1));
    }

    @Test
    public void testDetectsNoViolation() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/StrategyDummyData/StrategyInterface.class");
        ClassAdapter adapter2 = manager.createClassAdapter("./src/test/resources/StrategyDummyData/ConcreteStrategy.class");
        ClassAdapter adapter3 = manager.createClassAdapter("./src/test/resources/StrategyDummyData/ConcreteStrategy1.class");
        List<ClassAdapter> adapterList = new ArrayList<>();
        adapterList.add(adapter);
        adapterList.add(adapter2);
        adapterList.add(adapter3);
        Check check = new HollywoodPrincipleCheck();
        PresentationInformation info = check.check(adapterList, null);
        Assertions.assertTrue(info.passed);
        Assertions.assertEquals(CheckType.HollywoodPrinciple, info.checkName);
        Assertions.assertEquals(0, info.displayLines.size());
    }
}
