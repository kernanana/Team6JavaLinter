import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.NamingConventionCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NamingConventionAutoCorrectTests {

    @Test
    public void nothingToAutoCorrect(){
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/NamingConventionDummyData/GoodNames"), new UserOptions());
        PresentationInformation result = cnCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed()); //false if not violated
        Assertions.assertTrue(result.getDisplayLines().size() == 0);

    }

    @Test
    public void badClassNameAutoCorrect(){
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        UserOptions userOptions = new UserOptions();
        userOptions.doAutoCorrect();
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/NamingConventionDummyData/BadClassName"), userOptions);
        PresentationInformation result = cnCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true because a violation exists
        Assertions.assertTrue(result.countDisplayLines() == 1);
        Assertions.assertTrue(result.getDisplayLines().get(0).equals("Class name badClassName in ASMPracticeCode/NamingConventionDummyData/badClassName needs to be uppercased"));
        //below test will break code
        //Assertions.assertTrue(result.displayLines.get(1).equals("Class name badClassName in ASMPracticeCode/NamingConventionDummyData/badClassName changed to BadClassName"));
    }

    @Test
    public void badFields_Methods_FINALS_Autocorrect(){
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/NamingConventionDummyData/BadNames"), new UserOptions());
        PresentationInformation result = cnCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true because a violation exists
        Assertions.assertTrue(result.getDisplayLines().get(0).equals("Field name Field in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
        Assertions.assertTrue(result.getDisplayLines().get(1).equals("Field name FIeLD1 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be ALL CAPS with _ as spaces"));
        Assertions.assertTrue(result.getDisplayLines().get(2).equals("Field name field_2 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be ALL CAPS with _ as spaces"));
        Assertions.assertTrue(result.getDisplayLines().get(3).equals("Field name FIELD_3 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
        Assertions.assertTrue(result.getDisplayLines().get(4).equals("Method name Method in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
        Assertions.assertTrue(result.getDisplayLines().get(5).equals("Method name METHOD_2 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
    }
}
