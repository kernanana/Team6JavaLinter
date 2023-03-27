import DataSource.*;
import Domain.*;
import Domain.Checks.*;
import Presentation.ConsoleUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Main {

    private static final List<CheckType> availableChecks = new ArrayList<>();
    private static final Map<CheckType, Check> checkComposition = new HashMap<>();

    public static void main(String[] args) {
        initializeAvailableChecks();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        PlantClassUMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(new SourceStringReaderCreator()), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(checkComposition, projectDataManager, umlParser);
        ConsoleUI ui = new ConsoleUI(linter, availableChecks);
        ui.UIMain();
    }

    private static void initializeAvailableChecks() {
        registerCheck(CheckType.PoorNamingConvention, new NamingConventionCheck());
        registerCheck(CheckType.EqualsHashCode, new EqualsHashCodeCheck());
        registerCheck(CheckType.SingleResponsibilityPrinciple, new SingleResponsibilityPrincipleCheck());
        registerCheck(CheckType.InformationHidingViolation, new InformationHidingCheck());
        registerCheck(CheckType.SingletonPattern, new SingletonPatternCheck());
        registerCheck(CheckType.DecoratorPattern, new DecoratorPatternCheck());
        registerCheck(CheckType.StrategyPattern, new StrategyPatternCheck());
    }

    private static void registerCheck(CheckType checkType, Check check) {
        availableChecks.add(checkType);
        checkComposition.put(checkType, check);
    }

}
