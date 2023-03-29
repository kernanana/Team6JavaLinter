package DataSource;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class ConfigParser {

    public Map<String, Object> parseConfig(String directory) {

        try {
            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream(directory);
            return yaml.load(inputStream);
        } catch(FileNotFoundException e) {
            throw new RuntimeException("Config file could not be found at location: " + directory);
        }

    }

}