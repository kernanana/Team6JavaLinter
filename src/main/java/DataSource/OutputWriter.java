package DataSource;

import Domain.PresentationInformation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputWriter {
    List<PresentationInformation> pis;
    public OutputWriter(List<PresentationInformation> presentationInformations) {
        pis = presentationInformations;
    }

    public String saveOutput() throws IOException {
        FileWriter fw = new FileWriter("src/ReportFile.txt");
        for(int k = 0; k<pis.size(); k++){
            fw.write(pis.get(k).returnUIMessage());
            fw.write(System.getProperty("line.separator"));
            fw.write(pis.get(k).printToDisplay());
        }
        fw.flush();
        fw.close();
        return "ReportFile.txt generated in ../src/";
    }
}
