package Domain;

import Domain.Adapters.ClassAdapter;
import Domain.Checks.Check;
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

    public Linter(Map<CheckType, Check> checkComposition, ProjectDataManager projectDataManager, PlantClassUMLParser umlParser){
        this.umlParser = umlParser;
        this.projectDataManager = projectDataManager;
        this.checkComposition = checkComposition;
    }

    public List<PresentationInformation> runChecks(List<CheckType> checksToRun, String filePath, UserOptions userOptions){
        this.classAdapters = this.projectDataManager.generateClassAdapters(filePath);
        List<PresentationInformation> presentationInformations = new ArrayList<>();
        for (CheckType checkType : checksToRun){
            presentationInformations.add(checkComposition.get(checkType).check(this.classAdapters, userOptions));
        }
        if (userOptions.parseUml){
            this.umlParser.parseUML(this.classAdapters, userOptions.umlOutputDirectory);
        }
        return presentationInformations;
    }

}