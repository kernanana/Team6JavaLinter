import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Adapters.ClassAdapter;
import Domain.CheckType;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.NoFinalizerCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class NoFinalizerTests {
    @Test
    public void testDetectsViolation() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/FinalizerDummyData/HasFinalizer.class");
        List<ClassAdapter> adapterList = new ArrayList<>();
        adapterList.add(adapter);
        Check check = new NoFinalizerCheck();
        CheckData checkData = new CheckData(adapterList, null);
        PresentationInformation info = check.check(checkData);
        Assertions.assertFalse(info.passed);
        Assertions.assertEquals(CheckType.NoFinalizerCheck, info.checkName);
        String str = "Class: ASMPracticeCode/FinalizerDummyData/HasFinalizer contains finalize method with zero parameters";
        Assertions.assertEquals(str, info.displayLines.get(0));
    }

    @Test
    public void testDetectsNoViolation() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/FinalizerDummyData/HasFinalizerParams.class");
        List<ClassAdapter> adapterList = new ArrayList<>();
        adapterList.add(adapter);
        Check check = new NoFinalizerCheck();
        CheckData checkData = new CheckData(adapterList, null);
        PresentationInformation info = check.check(checkData);
        Assertions.assertTrue(info.passed);
        Assertions.assertEquals(CheckType.NoFinalizerCheck, info.checkName);
    }
}
