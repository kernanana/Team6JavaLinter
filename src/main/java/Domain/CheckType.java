package Domain;

import Domain.Checks.Check;
import Domain.Checks.NamingConventionCheck;

public enum CheckType {
    PoorNamingConvention,NoFinalizerCheck, EqualsHashCode, InformationHidingViolation,
    HollywoodPrinciple,SingleResponsibilityPrinciple,SingletonPattern,StrategyPattern,
    DecoratorPattern, RedundantInterface, ObserverPattern
}
