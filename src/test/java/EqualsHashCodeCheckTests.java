import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
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
        PresentationInformation result = hasEqualsHasCodeCheck.check(projectDataManager.generateClassAdapters("src/test/resources/EqualsHashCodeDummyData/hasEqualsNoHashCode"), new UserOptions());
        Assertions.assertFalse(result.passed); // Says a class failed
        Assertions.assertEquals("ASMPracticeCode/hasEqualsNoHashCode/hasEqualsNoHashCode missing hashCode method", result.displayLines.get(0)); //displays correct informaiton
    }

    @Test
    public void hasHashCodeNoEquals(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = hasEqualsHasCodeCheck.check(projectDataManager.generateClassAdapters("src/test/resources/EqualsHashCodeDummyData/hasHashCodeNoEquals"), new UserOptions());
        Assertions.assertFalse(result.passed); // Says a class failed
        Assertions.assertEquals("ASMPracticeCode/hasHashCodeNoEquals/hasHashCodeNoEquals missing equals method", result.displayLines.get(0)); //displays correct informaiton
    }
    @Test
    public void hasNeither(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = hasEqualsHasCodeCheck.check(projectDataManager.generateClassAdapters("src/test/resources/EqualsHashCodeDummyData/hasNeitherEqualsNorHashCode"), new UserOptions());
        Assertions.assertTrue(result.passed); // Says no classes failed
        Assertions.assertEquals(0, result.displayLines.size()); //No display lines because all classes passed
    }

    @Test
    public void hasBoth(){
        Check hasEqualsHasCodeCheck = new EqualsHashCodeCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = hasEqualsHasCodeCheck.check(projectDataManager.generateClassAdapters("src/test/resources/EqualsHashCodeDummyData/hasBothHashCodeAndEquals"), new UserOptions());
        Assertions.assertTrue(result.passed); // Says no classes failed
        Assertions.assertEquals(0, result.displayLines.size()); //No display lines because all classes passed
    }

}
