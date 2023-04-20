package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.UserOptions;

import java.util.List;

public interface CheckDataInterface {
    public abstract List<ClassAdapter> getClasses();

    public abstract UserOptions getUserOptions();

    public abstract boolean hasUserOptions();
}
