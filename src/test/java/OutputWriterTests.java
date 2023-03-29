import DataSource.OutputWriter;
import Domain.CheckType;
import Domain.PresentationInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OutputWriterTests {
    @Test
    public void TestOutputWriterEmptyString() throws IOException {
        String expected = "ReportFile.txt generated in ../src/";
        PresentationInformation pi = new PresentationInformation(CheckType.DataClass);
        List<PresentationInformation> pis = new ArrayList<>();
        pis.add(pi);
        OutputWriter outputWriter = new OutputWriter(pis);
        String actual = outputWriter.saveOutput();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void TestOutputWriterPasses() throws IOException {
        String expected = "ReportFile.txt generated in ../src/";
        PresentationInformation pi = new PresentationInformation(CheckType.DataClass);
        pi.passedCheck();
        List<PresentationInformation> pis = new ArrayList<>();
        pis.add(pi);
        OutputWriter outputWriter = new OutputWriter(pis);
        String actual = outputWriter.saveOutput();
        Assertions.assertEquals(actual, expected);
    }
    @Test
    public void TestOutputWriterWithMessage() throws IOException {
        String expected = "ReportFile.txt generated in ../src/";
        PresentationInformation pi = new PresentationInformation(CheckType.DataClass);
        pi.passedCheck();
        pi.addDisplayLine("This example string should will be put in .txt");
        List<PresentationInformation> pis = new ArrayList<>();
        pis.add(pi);
        OutputWriter outputWriter = new OutputWriter(pis);
        String actual = outputWriter.saveOutput();
        Assertions.assertEquals(actual, expected);
        File file = new File("src/ReportFile.txt");
        Scanner sc = new Scanner(file);
        String expectedMsg = "DataClass passed!";
        String actualMsg = sc.nextLine();
        Assertions.assertEquals(expectedMsg,actualMsg);
    }
}
