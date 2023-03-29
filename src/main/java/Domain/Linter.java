package Domain;

import Domain.Adapters.ClassAdapter;
import Domain.Checks.Check;
import Domain.Checks.CheckData;
import net.sourceforge.plantuml.graph2.Plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Linter {
    private ProjectDataManager projectDataManager;
    private Map<CheckType, Check> checkComposition;
    private List<ClassAdapter> classAdapters;
    private PlantClassUMLParser umlParser;

    public Linter(ProjectDataManager projectDataManager, PlantClassUMLParser umlParser){
        this.umlParser = umlParser;
        this.projectDataManager = projectDataManager;
    }

    public List<PresentationInformation> runChecks(List<CheckType> checksToRun, String filePath, UserOptions userOptions){
        this.classAdapters = this.projectDataManager.generateClassAdapters(filePath);
        List<PresentationInformation> presentationInformations = new ArrayList<>();
        for (CheckType checkType : checksToRun){
            Check checkToRun = checkComposition.get(checkType);
            presentationInformations.add(checkToRun.check(new CheckData(classAdapters, userOptions)));
        }
        if (userOptions.hasUMLParse()){
            this.umlParser.parseUML(this.classAdapters, userOptions.getUmlOutputDirectory());
        }
        return presentationInformations;
    }

    public void defineAvailableChecks(Map<CheckType, Check> checkComposition) {
        this.checkComposition = checkComposition;
    }
}