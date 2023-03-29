package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.util.ArrayList;
import java.util.List;

public class StrategyPatternCheck implements Check{
    @Override
    public PresentationInformation check(CheckData data) {
        PresentationInformation information = new PresentationInformation(CheckType.StrategyPattern);
        List<ClassAdapter> classes = data.getClasses();
        
        for (int x = 0; x < classes.size(); x++) {
            ClassAdapter abstractStrategy = classes.get(x);
            List<ClassAdapter> concreteStrategies = findLowerLevelClasses(abstractStrategy, classes);
            if (concreteStrategies.size() > 0) {
                String line = formatDisplayLine(abstractStrategy, concreteStrategies);
                information.addDisplayLine(line);
            }
        }

        if (information.countDisplayLines() > 0) {
            information.passedCheck();
        }

        return information;
    }

    private String formatDisplayLine(ClassAdapter abstractStrategy, List<ClassAdapter> concreteStrategies) {
        StringBuilder returnVal = new StringBuilder("Strategy Abstraction Class: " + abstractStrategy.getClassName() + ", Concrete Strategy Classes:");
        for (int x = 0; x < concreteStrategies.size(); x ++) {
            returnVal.append(" ").append(concreteStrategies.get(x).getClassName());
            if (x != concreteStrategies.size() -1) {
                returnVal.append(",");
            }
        }
        return returnVal.toString();
    }

    private List<ClassAdapter> findLowerLevelClasses(ClassAdapter strategyClass, List<ClassAdapter> classes) {
        List<ClassAdapter> lowerLevelClasses = new ArrayList<>();
        lowerLevelClasses.addAll(allClassesThatExtend(strategyClass.getClassName(), classes));
        lowerLevelClasses.addAll(allClassesThatImplement(strategyClass.getClassName(), classes));
        return lowerLevelClasses;
    }

    private List<ClassAdapter> allClassesThatExtend(String className, List<ClassAdapter> classes){
        List<ClassAdapter> extendingClasses = new ArrayList<>();
        for (ClassAdapter aClass : classes) {
            if (aClass.getExtends().equals(className)) {
                extendingClasses.add(aClass);
            }
        }
        return extendingClasses;
    }

    private List<ClassAdapter> allClassesThatImplement(String className, List<ClassAdapter> classes){
        List<ClassAdapter> implementingClasses = new ArrayList<>();
        for (ClassAdapter aClass : classes) {
            if (aClass.getInterfaces().contains(className)) {
                implementingClasses.add(aClass);
            }
        }
        return implementingClasses;
    }
}
