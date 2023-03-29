import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.DataClassCheck;
import Domain.Checks.InformationHidingCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataClassTests {
    @Test
    public void TestDataClasswithGetters(){
        Check check = new DataClassCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/GetterSetterDummyData/DataClass"), new UserOptions());
        PresentationInformation result = check.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true if Data class is detected
        Assertions.assertTrue(result.countDisplayLines() == 1);
        Assertions.assertTrue(result.getDisplayLines().get(0).equals("ASMPracticeCode/GetterSetterDummyData/DataClass is a Data Class"));
    }

    @Test
    public void TestDataClasswithGettersAndSetters(){
        Check check = new DataClassCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/DataClassDummyData/GetSetDataClass"), new UserOptions());
        PresentationInformation result = check.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true if Data class is detected
        Assertions.assertTrue(result.countDisplayLines() == 1);
        System.out.println(result.getDisplayLines().get(0));
        Assertions.assertTrue(result.getDisplayLines().get(0).equals("ASMPracticeCode/DataClassDummyData/GetSetDataClass/GetSetDataClass is a Data Class"));
    }

    @Test
    public void TestDataClasswithOnlyFields(){
        Check check = new DataClassCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/DataClassDummyData/PublicFieldDataClass"), new UserOptions());
        PresentationInformation result = check.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true if Data class is detected
        Assertions.assertTrue(result.countDisplayLines() == 1);
        Assertions.assertTrue(result.getDisplayLines().get(0).equals("ASMPracticeCode/DataClassDummyData/PublicFieldDataClass/PublicFieldDataClass is a Data Class"));
    }

    @Test
    public void TestNotADataClass(){
        Check check = new DataClassCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/EqualsHashCodeDummyData/hasEqualsNoHashCode"), new UserOptions());
        PresentationInformation result = check.check(checkData);
        Assertions.assertFalse(result.hasPassed()); //true if Data class is detected
        Assertions.assertTrue(result.countDisplayLines() == 0);
    }
}
