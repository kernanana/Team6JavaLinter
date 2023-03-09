package DataSource;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PrintWriterUMLTextWriter implements UMLTextWriter{

    @Override
    public void writeUMLText(String filepath, String umltext) {
        try (PrintWriter out = new PrintWriter(filepath + "\\generatedUML.txt")){
            out.println(umltext);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
