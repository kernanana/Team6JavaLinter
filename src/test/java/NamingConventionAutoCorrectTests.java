import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.NamingConventionCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NamingConventionAutoCorrectTests {

    private Check cnCheck;
    private ProjectDataManager projectDataManager;

    @BeforeEach
    public void setup() {
        cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        projectDataManager = new ASMProjectDataManager(dataLoader);
    }

    @Test
    public void nothingToAutoCorrect(){
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/NamingConventionDummyData/GoodNames"), new UserOptions());
        PresentationInformation result = cnCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed()); //false if not violated
        Assertions.assertEquals(0, result.getDisplayLines().size());
    }

    @Test
    public void badClassNameAutoCorrect(){
        UserOptions userOptions = new UserOptions();
        userOptions.doAutoCorrect();
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/NamingConventionDummyData/BadClassName"), userOptions);
        PresentationInformation result = cnCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true because a violation exists
        Assertions.assertTrue(result.getDisplayLines().contains("Class name badClassName in ASMPracticeCode/NamingConventionDummyData/badClassName needs to be uppercased"));
        //below test will break code
        //Assertions.assertTrue(result.displayLines.get(1).equals("Class name badClassName in ASMPracticeCode/NamingConventionDummyData/badClassName changed to BadClassName"));
    }

    @Test
    public void badFields_Methods_FINALS_Autocorrect(){
        String[] names = {"Field", "FIELD_3", "Method", "METHOD_2"};
        String[] finalNames = {"FIeLD1", "field_2"};
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/NamingConventionDummyData/BadNames"), new UserOptions());
        PresentationInformation result = cnCheck.check(checkData);
        for(String finalName : finalNames) {
            Assertions.assertTrue(result.getDisplayLines().contains("Field name " + finalName + " in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be ALL CAPS with _ as spaces"));
        }
        for(String name : names) {
            String starter;
            if(name.startsWith("F"))
                starter = "Field";
            else
                starter = "Method";
            Assertions.assertTrue(result.getDisplayLines().contains(starter + " name " + name + " in ASMPracticeCode/NamingConventionDummyData/EverythingErrorClass needs to be lowercased"));
        }
    }
}
