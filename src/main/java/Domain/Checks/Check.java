package Domain.Checks;

import Domain.PresentationInformation;

public interface Check {
    public abstract PresentationInformation check(CheckData data);
}
