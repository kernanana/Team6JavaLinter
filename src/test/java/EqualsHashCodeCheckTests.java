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
    @Test
    public void hasEqualsNoHashCode(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/EqualsHashCodeDummyData/hasEqualsNoHashCode"), new UserOptions());
        PresentationInformation result = hasEqualsHasCodeCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed()); // Says a class failed
        Assertions.assertEquals("ASMPracticeCode/hasEqualsNoHashCode/hasEqualsNoHashCode missing hashCode method", result.getDisplayLines().get(0)); //displays correct informaiton
    }

    @Test
    public void hasHashCodeNoEquals(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/EqualsHashCodeDummyData/hasHashCodeNoEquals"), new UserOptions());
        PresentationInformation result = hasEqualsHasCodeCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed()); // Says a class failed
        Assertions.assertEquals("ASMPracticeCode/hasHashCodeNoEquals/hasHashCodeNoEquals missing equals method", result.getDisplayLines().get(0)); //displays correct informaiton
    }
    @Test
    public void hasNeither(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/EqualsHashCodeDummyData/hasNeitherEqualsNorHashCode"), new UserOptions());
        PresentationInformation result = hasEqualsHasCodeCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); // Says no classes failed
        Assertions.assertEquals(0, result.countDisplayLines()); //No display lines because all classes passed
    }

    @Test
    public void hasBoth(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/EqualsHashCodeDummyData/hasBothHashCodeAndEquals"), new UserOptions());
        PresentationInformation result = hasEqualsHasCodeCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); // Says no classes failed
        Assertions.assertEquals(0, result.countDisplayLines()); //No display lines because all classes passed
    }

}
