package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.UserOptions;

import java.util.List;

public class CheckData {
    private final List<ClassAdapter> classes;
    private final UserOptions userOptions;

    public CheckData(List<ClassAdapter> classes, UserOptions userOptions) {
        this.classes = classes;
        this.userOptions = userOptions;
    }

    public List<ClassAdapter> getClasses() {
        return classes;
    }

    public UserOptions getUserOptions() {
        return userOptions;
    }


}
