package Domain.Checks;

import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Adapters.ClassAdapter;
import Domain.ProjectDataManager;
import Domain.UserOptions;

import java.util.List;

public class MockCheckData implements CheckDataInterface{
    private final List<ClassAdapter> classes;
    private final UserOptions userOptions;

    public MockCheckData(List<ClassAdapter> classes, UserOptions userOptions) {
        this.classes = classes;
        this.userOptions = userOptions;
    }

    public MockCheckData(String path){
        DefaultDataLoader dataLoader = new DefaultDataLoader();
        ProjectDataManager projectDataManager = new ASMProjectDataManager(dataLoader);
        classes = projectDataManager.generateClassAdapters(path);
        userOptions = new UserOptions();

    }

    public List<ClassAdapter> getClasses() {
        return classes;
    }

    public UserOptions getUserOptions() {
        return userOptions;
    }

    public boolean hasUserOptions() {
        return userOptions != null && userOptions.isDefined();
    }


}
