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

public class NamingConventionCheckTests {
    @Test
    public void badFields_Methods_FINALS(){
        String[] names = {"Field", "FIELD_3", "Method", "METHOD_2"};
        String[] finalNames = {"FIeLD1", "field_2"};
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/NamingConventionDummyData/BadNames"), new UserOptions());
        PresentationInformation result = cnCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true because a violation exists
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

    @Test
    public void allGoodNames(){
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/NamingConventionDummyData/GoodNames"), new UserOptions());
        PresentationInformation result = cnCheck.check(checkData);
        Assertions.assertFalse(result.hasPassed()); //false if not violated
        Assertions.assertEquals(0, result.countDisplayLines());
    }

    @Test
    public void badClassName(){
        Check cnCheck = new NamingConventionCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/NamingConventionDummyData/BadClassName"), new UserOptions());
        PresentationInformation result = cnCheck.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //true because a violation exists
        Assertions.assertTrue(result.getDisplayLines().contains("Class name badClassName in ASMPracticeCode/NamingConventionDummyData/badClassName needs to be uppercased"));
    }


}
