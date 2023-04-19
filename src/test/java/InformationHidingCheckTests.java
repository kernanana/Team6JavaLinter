import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import Domain.Checks.InformationHidingCheck;
import Domain.Checks.NamingConventionCheck;
import Domain.PresentationInformation;
import Domain.ProjectDataManager;
import Domain.UserOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InformationHidingCheckTests {

    @Test
    public void dataClassNotFlagged(){
        Check check = new InformationHidingCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/GetterSetterDummyData/DataClass"), new UserOptions());
        PresentationInformation result = check.check(checkData);
        Assertions.assertFalse(result.hasPassed()); //false if no getters/setters or is a dataclass
        Assertions.assertTrue(result.countDisplayLines() == 1);
        Assertions.assertTrue(result.getDisplayLines().get(0).equals("Getter/Setter detected in ASMPracticeCode/GetterSetterDummyData/DataClass, but ignored since it is a data class"));
    }

    @Test
    public void noGetterSetters(){
        Check check = new InformationHidingCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/GetterSetterDummyData/NoGetterSetter"), new UserOptions());
        PresentationInformation result = check.check(checkData);
        Assertions.assertFalse(result.hasPassed()); //false if no getters/setters or is a dataclass
        Assertions.assertTrue(result.countDisplayLines() == 0);
    }

    @Test
    public void GetterSetters(){
        String[] setters = {"setNumber", "setString"};
        String[] getters = {"getNumber", "getString"};
        Check check = new InformationHidingCheck();
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        CheckData checkData = new CheckData(projectDataManager.generateClassAdapters("./src/test/resources/GetterSetterDummyData/GetterSetter"), new UserOptions());
        PresentationInformation result = check.check(checkData);
        Assertions.assertTrue(result.hasPassed()); //false if no getters/setters or is a dataclass
        for(String setter : setters)
            Assertions.assertTrue(result.getDisplayLines().contains(setter + " in ASMPracticeCode/GetterSetterDummyData/GetterSetterClass is a Setter"));
        for(String getter : getters)
            Assertions.assertTrue(result.getDisplayLines().contains(getter + " in ASMPracticeCode/GetterSetterDummyData/GetterSetterClass is a Getter"));
        //Assertions.assertTrue(result.displayLines.size() == 0);
    }
}
