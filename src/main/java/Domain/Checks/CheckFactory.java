package Domain.Checks;

import Domain.CheckType;

public class CheckFactory {

    public static Check getCheck(CheckType checkType) {
        switch(checkType) {
            case PoorNamingConvention: return new NamingConventionCheck();
            case NoFinalizerCheck: return new NoFinalizerCheck();
            case EqualsHashCode: return new EqualsHashCodeCheck();
            case InformationHidingViolation: return new InformationHidingCheck();
            case HollywoodPrinciple: return new HollywoodPrincipleCheck();
            case SingleResponsibilityPrinciple: return new SingleResponsibilityPrincipleCheck();
            case SingletonPattern: return new SingletonPatternCheck();
            case StrategyPattern: return new StrategyPatternCheck();
            case DecoratorPattern: return new DecoratorPatternCheck();
            case RedundantInterface: return new RedundantInterface();
            case DataClass: return new DataClassCheck();
            case ObserverPattern: return new ObserverPatternCheck();
        }
        return null;
    }
}
