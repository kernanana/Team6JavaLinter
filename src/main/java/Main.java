import DataSource.*;
import Domain.*;
import Domain.Checks.*;
import Presentation.ConsoleUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Main {

    private static final String CONFIG_PATH = "./config.yaml";
    private static final List<CheckType> availableChecks = new ArrayList<>();
    private static final Map<CheckType, Check> checkComposition = new HashMap<>();
    private static final Map<String, Object> config = new ConfigParser().parseConfig(CONFIG_PATH);

    public static void main(String[] args) {
        initializeAvailableChecks();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(new SourceStringReaderCreator()), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(checkComposition, projectDataManager, umlParser);
        ConsoleUI ui = new ConsoleUI(linter, availableChecks, config);
        ui.UIMain();
    }

    private static void initializeAvailableChecks() {
        registerCheck("PoorNamingConvention", CheckType.PoorNamingConvention, new NamingConventionCheck());
        registerCheck("EqualsHashCode", CheckType.EqualsHashCode, new EqualsHashCodeCheck());
        registerCheck("SingleResponsibilityPrinciple", CheckType.SingleResponsibilityPrinciple, new SingleResponsibilityPrincipleCheck());
        registerCheck("InformationHidingViolation", CheckType.InformationHidingViolation, new InformationHidingCheck());
        registerCheck("SingletonPattern", CheckType.SingletonPattern, new SingletonPatternCheck());
        registerCheck("DecoratorPattern", CheckType.DecoratorPattern, new DecoratorPatternCheck());
        registerCheck("StrategyPattern", CheckType.StrategyPattern, new StrategyPatternCheck());
        registerCheck("RedundantInterface", CheckType.RedundantInterface, new RedundantInterface());
    }

    private static void registerCheck(String checkName, CheckType checkType, Check check) {
        Map<String, Boolean> checks = (Map<String, Boolean>) config.get("checks");

        if(checks.getOrDefault(checkName, false)) {
            availableChecks.add(checkType);
            checkComposition.put(checkType, check);
        }
    }

}
