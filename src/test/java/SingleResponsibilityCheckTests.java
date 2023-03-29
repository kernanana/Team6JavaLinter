
import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
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
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/SingleResponsibilityPrincipleDummyData/SingleResponsibilityCheckAllInOne"), userOptions);
        PresentationInformation result = singleResponsibilityCheck.check(checkData);
        Assertions.assertEquals("The median amount of public methods is 2. We will consider over 2 public methods a violation", result.getDisplayLines().get(0)); //displays correct informaiton, classes under test have 1, 2, and 3 public methods respectivly
    }

    @Test
    public void canUseSelectedAmount(){
        Check singleResponsibilityCheck = new SingleResponsibilityPrincipleCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        UserOptions userOptions = new UserOptions();
        userOptions.defineMaxMethods(0); //Set to non-default value
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/SingleResponsibilityPrincipleDummyData/SingleResponsibilityCheckAllInOne"), userOptions);
        PresentationInformation result = singleResponsibilityCheck.check(checkData);
        Assertions.assertEquals("The maximum amount of public methods is 0. We will consider over 0 public methods a violation", result.getDisplayLines().get(0)); //displays correct informaiton, classes under test have 1, 2, and 3 public methods respectivly
    }

    @Test
    public void canIdentifyIncorrectClassesWithMedian(){
        Check singleResponsibilityCheck = new SingleResponsibilityPrincipleCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        UserOptions userOptions = new UserOptions();
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/SingleResponsibilityPrincipleDummyData/SingleResponsibilityCheckAllInOne"), userOptions);
        PresentationInformation result = singleResponsibilityCheck.check(checkData);
        Assertions.assertEquals("ASMPracticeCode/SingleResponsibilityCheckAllInOne/IHaveThreePublicMethods has 3 public methods", result.getDisplayLines().get(1)); //displays correct informaiton, classes under test have 1, 2, and 3 public methods respectivly, only the one with 3 should fail
        Assertions.assertEquals(2, result.countDisplayLines()); //Only line stating max amount and the one class that failed
    }

    @Test
    public void canIdentifyIncorrectClassesWithSelectedAmount(){
        Check singleResponsibilityCheck = new SingleResponsibilityPrincipleCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        UserOptions userOptions = new UserOptions();
        userOptions.defineMaxMethods(1); //Set to one
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/SingleResponsibilityPrincipleDummyData/SingleResponsibilityCheckAllInOne"), userOptions);
        PresentationInformation result = singleResponsibilityCheck.check(checkData);
        Assertions.assertEquals("ASMPracticeCode/SingleResponsibilityCheckAllInOne/IHaveTwoPublicMethods has 2 public methods", result.getDisplayLines().get(1)); //displays correct informaiton, classes under test have 1, 2, and 3 public methods respectivly, 2 and 3 fail here
        Assertions.assertEquals("ASMPracticeCode/SingleResponsibilityCheckAllInOne/IHaveThreePublicMethods has 3 public methods", result.getDisplayLines().get(2)); //displays correct informaiton, classes under test have 1, 2, and 3 public methods respectivly, 2 and 3 fail here
        Assertions.assertEquals(3, result.countDisplayLines()); //Only line stating max amount and the two classes that failed
    }

    @Test
    public void doesNotCountPublicMethods(){
        Check singleResponsibilityCheck = new SingleResponsibilityPrincipleCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        UserOptions userOptions = new UserOptions();
        userOptions.defineMaxMethods(0); //Set to zero
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/SingleResponsibilityPrincipleDummyData/AllPrivate"), userOptions);
        PresentationInformation result = singleResponsibilityCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //Passed as class only has 2 private methods
        Assertions.assertEquals(1, result.getDisplayLines().size()); //None fail, only displays amount that is considered max
    }
}
