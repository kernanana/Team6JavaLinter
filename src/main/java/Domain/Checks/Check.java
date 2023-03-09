package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.util.List;

public interface Check {
    public abstract PresentationInformation check(List<ClassAdapter> classes, UserOptions userOptions);
}
