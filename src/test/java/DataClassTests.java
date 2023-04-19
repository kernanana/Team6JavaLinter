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

    private CheckData setUpCheckData(String filepath) {
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        return new CheckData(projectDataManager.generateClassAdapters(filepath), new UserOptions());
    }

    @Test
    public void TestDataClasswithGetters(){
        Check check = new DataClassCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/GetterSetterDummyData/DataClass");
        PresentationInformation result = check.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true if Data class is detected
        Assertions.assertTrue(result.countDisplayLines() == 1);
        Assertions.assertTrue(result.getDisplayLines().get(0).equals("ASMPracticeCode/GetterSetterDummyData/DataClass is a Data Class"));
    }

    @Test
    public void TestDataClasswithGettersAndSetters(){
        Check check = new DataClassCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/DataClassDummyData/GetSetDataClass");
        PresentationInformation result = check.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true if Data class is detected
        Assertions.assertTrue(result.countDisplayLines() == 1);
        Assertions.assertTrue(result.getDisplayLines().get(0).equals("ASMPracticeCode/DataClassDummyData/GetSetDataClass/GetSetDataClass is a Data Class"));
    }

    @Test
    public void TestDataClasswithOnlyFields(){
        Check check = new DataClassCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/DataClassDummyData/PublicFieldDataClass");
        PresentationInformation result = check.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true if Data class is detected
        Assertions.assertTrue(result.countDisplayLines() == 1);
        Assertions.assertTrue(result.getDisplayLines().get(0).equals("ASMPracticeCode/DataClassDummyData/PublicFieldDataClass/PublicFieldDataClass is a Data Class"));
    }

    @Test
    public void TestNotADataClass(){
        Check check = new DataClassCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/EqualsHashCodeDummyData/hasEqualsNoHashCode");
        PresentationInformation result = check.check(checkData);
        Assertions.assertFalse(result.hasPassed()); //true if Data class is detected
        Assertions.assertTrue(result.countDisplayLines() == 0);
    }
}
