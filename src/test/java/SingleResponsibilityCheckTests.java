
import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.SingleResponsibilityPrincipleCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SingleResponsibilityCheckTests {
    @Test
    public void properlyFindsMedian(){
        Check singleResponsibilityCheck = new SingleResponsibilityPrincipleCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        UserOptions userOptions = new UserOptions();
        userOptions.maximumMethods = -1; //Set default
        PresentationInformation result = singleResponsibilityCheck.check(projectDataManager.generateClassAdapters("./src/test/resources/SingleResponsibilityPrincipleDummyData/SingleResponsibilityCheckAllInOne"), userOptions);
        Assertions.assertEquals("The median amount of public methods is 2. We will consider over 2 public methods a violation", result.displayLines.get(0)); //displays correct informaiton, classes under test have 1, 2, and 3 public methods respectivly
    }

    @Test
    public void canUseSelectedAmount(){
        Check singleResponsibilityCheck = new SingleResponsibilityPrincipleCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        UserOptions userOptions = new UserOptions();
        userOptions.maximumMethods = 0; //Set to non-default value
        PresentationInformation result = singleResponsibilityCheck.check(projectDataManager.generateClassAdapters("./src/test/resources/SingleResponsibilityPrincipleDummyData/SingleResponsibilityCheckAllInOne"), userOptions);
        Assertions.assertEquals("The maximum amount of public methods is 0. We will consider over 0 public methods a violation", result.displayLines.get(0)); //displays correct informaiton, classes under test have 1, 2, and 3 public methods respectivly
    }

    @Test
    public void canIdentifyIncorrectClassesWithMedian(){
        Check singleResponsibilityCheck = new SingleResponsibilityPrincipleCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        UserOptions userOptions = new UserOptions();
        userOptions.maximumMethods = -1; //Set default
        PresentationInformation result = singleResponsibilityCheck.check(projectDataManager.generateClassAdapters("./src/test/resources/SingleResponsibilityPrincipleDummyData/SingleResponsibilityCheckAllInOne"), userOptions);
        Assertions.assertEquals("ASMPracticeCode/SingleResponsibilityCheckAllInOne/IHaveThreePublicMethods has 3 public methods", result.displayLines.get(1)); //displays correct informaiton, classes under test have 1, 2, and 3 public methods respectivly, only the one with 3 should fail
        Assertions.assertEquals(2, result.displayLines.size()); //Only line stating max amount and the one class that failed
    }

    @Test
    public void canIdentifyIncorrectClassesWithSelectedAmount(){
        Check singleResponsibilityCheck = new SingleResponsibilityPrincipleCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        UserOptions userOptions = new UserOptions();
        userOptions.maximumMethods = 1; //Set to one
        PresentationInformation result = singleResponsibilityCheck.check(projectDataManager.generateClassAdapters("./src/test/resources/SingleResponsibilityPrincipleDummyData/SingleResponsibilityCheckAllInOne"), userOptions);
        Assertions.assertEquals("ASMPracticeCode/SingleResponsibilityCheckAllInOne/IHaveTwoPublicMethods has 2 public methods", result.displayLines.get(1)); //displays correct informaiton, classes under test have 1, 2, and 3 public methods respectivly, 2 and 3 fail here
        Assertions.assertEquals("ASMPracticeCode/SingleResponsibilityCheckAllInOne/IHaveThreePublicMethods has 3 public methods", result.displayLines.get(2)); //displays correct informaiton, classes under test have 1, 2, and 3 public methods respectivly, 2 and 3 fail here
        Assertions.assertEquals(3, result.displayLines.size()); //Only line stating max amount and the two classes that failed
    }

    @Test
    public void doesNotCountPublicMethods(){
        Check singleResponsibilityCheck = new SingleResponsibilityPrincipleCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        UserOptions userOptions = new UserOptions();
        userOptions.maximumMethods = 0; //Set to zero
        PresentationInformation result = singleResponsibilityCheck.check(projectDataManager.generateClassAdapters("./src/test/resources/SingleResponsibilityPrincipleDummyData/AllPrivate"), userOptions);
        Assertions.assertTrue(result.passed); //Passed as class only has 2 private methods
        Assertions.assertEquals(1, result.displayLines.size()); //None fail, only displays amount that is considered max
    }
}
