import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Adapters.ClassAdapter;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.NoFinalizerCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class NoFinalizerTests {
    private ProjectDataManager manager;
    private Check check;

    @BeforeEach
    public void setup() {
        manager = new ASMProjectDataManager(new DefaultDataLoader());
        check = new NoFinalizerCheck();
    }

    private PresentationInformation createCheckInfo(String classFile) {
        ClassAdapter adapter = manager.createClassAdapter(classFile);
        List<ClassAdapter> adapterList = new ArrayList<>();
        adapterList.add(adapter);
        CheckData checkData = new CheckData(adapterList, null);
        return check.check(checkData);
    }

    @Test
    public void testDetectsViolation() {
        PresentationInformation info = createCheckInfo("./src/test/resources/FinalizerDummyData/HasFinalizer.class");
        Assertions.assertFalse(info.hasPassed());
        String str = "Class: ASMPracticeCode/FinalizerDummyData/HasFinalizer contains finalize method with zero parameters";
        Assertions.assertEquals(str, info.getDisplayLines().get(0));
    }

    @Test
    public void testDetectsNoViolation() {
        PresentationInformation info = createCheckInfo("./src/test/resources/FinalizerDummyData/HasFinalizerParams.class");
        Assertions.assertTrue(info.hasPassed());
    }

}
