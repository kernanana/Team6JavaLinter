package Domain;

import DataSource.DataLoader;
import DataSource.DefaultDataLoader;
import Domain.Adapters.ClassAdapter;

import java.io.File;
import java.util.*;

public abstract class ProjectDataManager {
    protected DataLoader dataLoader;

    public ProjectDataManager(DataLoader dataLoader){
        this.dataLoader = dataLoader;
    }

    public final List<ClassAdapter> generateClassAdapters(String filepath){
        ArrayList<ClassAdapter> classAdapters = new ArrayList<>();
        File folder = new File(filepath);
        Queue<File> listOfFiles = new LinkedList<>(Arrays.asList(Objects.requireNonNull(folder.listFiles())));
        while (!listOfFiles.isEmpty()) {
            File file = listOfFiles.poll();
            if (file.isFile()) {
                if (file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("class")){
                    classAdapters.add(this.createClassAdapter(file.getAbsolutePath()));
                }
            }else{
                listOfFiles.addAll(Arrays.asList(Objects.requireNonNull(file.listFiles())));
            }
        }
        return classAdapters;
    }

    public abstract ClassAdapter createClassAdapter(String path);



}
