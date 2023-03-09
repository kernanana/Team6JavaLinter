package DataSource;

import net.sourceforge.plantuml.SourceStringReader;

public class SourceStringReaderCreator {
    public SourceStringReader create(String source){
        return new SourceStringReader(source);
    }
}
