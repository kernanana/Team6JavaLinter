import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.NamingConventionCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NamingConventionCheckTests {

    @Test
    public void allGoodNames(){
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = cnCheck.check(projectDataManager.generateClassAdapters("src/test/resources/NamingConventionDummyData/GoodNames"), new UserOptions());
        Assertions.assertFalse(result.passed); //false if not violated
        Assertions.assertTrue(result.displayLines.size() == 0);
    }

    @Test
    public void badClassName(){
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = cnCheck.check(projectDataManager.generateClassAdapters("src/test/resources/NamingConventionDummyData/BadClassName"), new UserOptions());
        Assertions.assertTrue(result.passed); //true because a violation exists
        Assertions.assertTrue(result.displayLines.size() == 1);
        Assertions.assertTrue(result.displayLines.get(0).equals("Class name badClassName in ASMPracticeCode/NamingConventionDummyData/badClassName needs to be uppercased"));
    }

    @Test
    public void badFields_Methods_FINALS(){
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = cnCheck.check(projectDataManager.generateClassAdapters("src/test/resources/NamingConventionDummyData/BadNames"), new UserOptions());
        Assertions.assertTrue(result.passed); //true because a violation exists
        Assertions.assertTrue(result.displayLines.size() == 6);
        Assertions.assertTrue(result.displayLines.get(0).equals("Field name Field in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
        Assertions.assertTrue(result.displayLines.get(1).equals("Field name FIeLD1 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be ALL CAPS with _ as spaces"));
        Assertions.assertTrue(result.displayLines.get(2).equals("Field name field_2 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be ALL CAPS with _ as spaces"));
        Assertions.assertTrue(result.displayLines.get(3).equals("Field name FIELD_3 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
        Assertions.assertTrue(result.displayLines.get(4).equals("Method name Method in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
        Assertions.assertTrue(result.displayLines.get(5).equals("Method name METHOD_2 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
    }

}
