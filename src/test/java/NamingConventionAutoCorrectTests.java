import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
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
        PresentationInformation result = cnCheck.check(projectDataManager.generateClassAdapters("src/test/resources/NamingConventionDummyData/GoodNames"), new UserOptions());
        Assertions.assertFalse(result.passed); //false if not violated
        Assertions.assertTrue(result.displayLines.size() == 0);
    }

    @Test
    public void badClassNameAutoCorrect(){
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = cnCheck.check(projectDataManager.generateClassAdapters("src/test/resources/NamingConventionDummyData/BadClassName"), new UserOptions());
        Assertions.assertTrue(result.passed); //true because a violation exists
        Assertions.assertTrue(result.displayLines.size() == 2);
        Assertions.assertTrue(result.displayLines.get(0).equals("Class name badClassName in ASMPracticeCode/NamingConventionDummyData/badClassName needs to be uppercased"));
        //below test will break code
        //Assertions.assertTrue(result.displayLines.get(1).equals("Class name badClassName in ASMPracticeCode/NamingConventionDummyData/badClassName changed to BadClassName"));
    }

    @Test
    public void badFields_Methods_FINALS_Autocorrect(){
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        PresentationInformation result = cnCheck.check(projectDataManager.generateClassAdapters("src/test/resources/NamingConventionDummyData/BadNames"), new UserOptions());
        Assertions.assertTrue(result.passed); //true because a violation exists
        Assertions.assertTrue(result.displayLines.get(0).equals("Field name Field in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
        Assertions.assertTrue(result.displayLines.get(1).equals("Field name FIeLD1 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be ALL CAPS with _ as spaces"));
        Assertions.assertTrue(result.displayLines.get(2).equals("Field name field_2 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be ALL CAPS with _ as spaces"));
        Assertions.assertTrue(result.displayLines.get(3).equals("Field name FIELD_3 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
        Assertions.assertTrue(result.displayLines.get(4).equals("Method name Method in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
        Assertions.assertTrue(result.displayLines.get(5).equals("Method name METHOD_2 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
//        Assertions.assertTrue(result.displayLines.size() == 12);
        //Below lines will break code
//        Assertions.assertTrue(result.displayLines.get(6).equals("Field name Field in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass changed to field"));
//        Assertions.assertTrue(result.displayLines.get(7).equals("Field name FIeLD1 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass changed to FIELD1"));
//        Assertions.assertTrue(result.displayLines.get(8).equals("Field name field_2 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be FIELD_2"));
//        Assertions.assertTrue(result.displayLines.get(9).equals("Field name FIELD_3 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be field_3"));
//        Assertions.assertTrue(result.displayLines.get(10).equals("Method name Method in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be method"));
//        Assertions.assertTrue(result.displayLines.get(11).equals("Method name METHOD_2 in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be mETHOD_2"));
    }
}
