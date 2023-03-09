package DataSource;

import java.io.File;
import java.io.IOException;

public class PlantUMLSourceStringReader extends SourceStringReaderAdapter{
    private SourceStringReaderCreator sourceStringReaderCreator;

    public PlantUMLSourceStringReader(SourceStringReaderCreator sourceStringReaderCreator){
        this.sourceStringReaderCreator = sourceStringReaderCreator;
    }

    @Override
    public void generateImage(String source, File outputFile) {
        try {
            this.sourceStringReaderCreator.create(source).generateImage(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
