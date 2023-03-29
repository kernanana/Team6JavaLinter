package DataSource;

import net.sourceforge.plantuml.SourceStringReader;

import java.io.File;
import java.io.IOException;

public class PlantUMLSourceStringReader extends SourceStringReaderAdapter{
    public PlantUMLSourceStringReader(){
    }

    @Override
    public void generateImage(String source, File outputFile) {
        try {
            SourceStringReader reader = new SourceStringReader(source);
            reader.generateImage(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
