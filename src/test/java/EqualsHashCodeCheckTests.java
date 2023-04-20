import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.EqualsHashCodeCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
public class EqualsHashCodeCheckTests {
    private CheckData setUpCheckData(String filepath) {
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        return new CheckData(projectDataManager.generateClassAdapters(filepath), new UserOptions());
    }

    @Test
    public void hasEqualsNoHashCode(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/EqualsHashCodeDummyData/hasEqualsNoHashCode");
        PresentationInformation result = hasEqualsHasCodeCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed()); // Says a class failed
        Assertions.assertEquals("ASMPracticeCode/hasEqualsNoHashCode/hasEqualsNoHashCode missing hashCode method", result.getDisplayLines().get(0)); //displays correct informaiton
    }

    @Test
    public void hasHashCodeNoEquals(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/EqualsHashCodeDummyData/hasHashCodeNoEquals");
        PresentationInformation result = hasEqualsHasCodeCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed()); // Says a class failed
        Assertions.assertEquals("ASMPracticeCode/hasHashCodeNoEquals/hasHashCodeNoEquals missing equals method", result.getDisplayLines().get(0)); //displays correct informaiton
    }
    @Test
    public void hasNeither(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/EqualsHashCodeDummyData/hasNeitherEqualsNorHashCode");
        PresentationInformation result = hasEqualsHasCodeCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); // Says no classes failed
        Assertions.assertEquals(0, result.countDisplayLines()); //No display lines because all classes passed
    }

    @Test
    public void hasBoth(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        CheckData checkData = setUpCheckData("./src/test/resources/EqualsHashCodeDummyData/hasBothHashCodeAndEquals");
        PresentationInformation result = hasEqualsHasCodeCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); // Says no classes failed
        Assertions.assertEquals(0, result.countDisplayLines()); //No display lines because all classes passed
    }

}
