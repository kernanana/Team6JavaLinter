import DataSource.*;
import Domain.*;
import Domain.Checks.*;
import Presentation.ConsoleUI;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args){
        ArrayList<CheckType> availibleChecks = new ArrayList<>();
        HashMap<CheckType, Check> checkComposition = new HashMap<>();
        availibleChecks.add(CheckType.PoorNamingConvention);
        checkComposition.put(CheckType.PoorNamingConvention, new NamingConventionCheck());
        availibleChecks.add(CheckType.EqualsHashCode);
        checkComposition.put(CheckType.EqualsHashCode, new EqualsHashCodeCheck());
        availibleChecks.add(CheckType.SingleResponsibilityPrinciple);
        checkComposition.put(CheckType.SingleResponsibilityPrinciple, new SingleResponsibilityPrincipleCheck());
        availibleChecks.add(CheckType.InformationHidingViolation);
        checkComposition.put(CheckType.InformationHidingViolation, new InformationHidingCheck());
        availibleChecks.add(CheckType.SingletonPattern);
        checkComposition.put(CheckType.SingletonPattern, new SingletonPatternCheck());
        availibleChecks.add(CheckType.DecoratorPattern);
        checkComposition.put(CheckType.DecoratorPattern, new DecoratorPatternCheck());
        availibleChecks.add(CheckType.StrategyPattern);
        checkComposition.put(CheckType.StrategyPattern, new StrategyPatternCheck());
        ProjectDataManager projectDataManager = new ASMProjectDataManager(new DefaultDataLoader());
        UMLParser umlParser = new PlantClassUMLParser(new PlantUMLSourceStringReader(new SourceStringReaderCreator()), new PrintWriterUMLTextWriter());
        Linter linter = new Linter(checkComposition, projectDataManager, umlParser);
        ConsoleUI ui = new ConsoleUI(linter, availibleChecks);
        ui.UIMain();
    }

}
