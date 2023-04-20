package Domain.Checks;

import Domain.PresentationInformation;

public interface Check {
    public abstract PresentationInformation check(CheckDataInterface data);
}
