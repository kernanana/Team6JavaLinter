package Domain;

public class UserOptions {
    private int maximumMethods;
    private String umlOutputDirectory;
    private boolean parseUml;
    private boolean namingConventionAutoCorrect;

    public UserOptions() {
        maximumMethods = -1;
        namingConventionAutoCorrect = false;
        umlOutputDirectory = "";
        parseUml = false;
    }

    public boolean isDefined() {
        return (maximumMethods != -1) || !namingConventionAutoCorrect || !umlOutputDirectory.isEmpty() || !parseUml;
    }

    public void defineMaxMethods(int max) {
        maximumMethods = max;
    }

    public void doAutoCorrect() {
        namingConventionAutoCorrect = true;
    }

    public void doUMLParse(String outputDirectory) {
        parseUml = true;
        umlOutputDirectory = outputDirectory;
    }

    public boolean hasUMLParse() {
        return parseUml;
    }

    public boolean hasAutoCorrect() {
        return namingConventionAutoCorrect;
    }

    public boolean maxMethodsIsDefined() {
        return maximumMethods != -1;
    }

    public int getMaximumMethods() {
        return maximumMethods;
    }

    public String getUmlOutputDirectory() {
        return umlOutputDirectory;
    }
}
