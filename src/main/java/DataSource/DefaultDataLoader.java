package DataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DefaultDataLoader implements  DataLoader{
    public byte[] loadFileBytes(String name){
        byte[] arr = null;
        try {
            File file = new File(name);
            FileInputStream fl = new FileInputStream(file);
            arr = new byte[(int)file.length()];
            fl.read(arr);
            fl.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
