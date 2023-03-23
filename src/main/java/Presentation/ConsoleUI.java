package Presentation;

import Domain.CheckType;
import Domain.Linter;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleUI extends UI {


    public ConsoleUI(Linter linter, List<CheckType> availibleChecks) {
        super(linter, availibleChecks);
    }

    @Override
    public void UIMain() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("What checks would you like to perform?");
        for (int i = 0; i < this.availibleChecks.size(); i++){
            System.out.println((i + 1) + ". " + this.availibleChecks.get(i).toString());
        }
        System.out.println("Example answer: 1,3,2");
        try {
            String checksToPerform = reader.readLine();
            String[] splitChecksToPerform = checksToPerform.split(",");
            ArrayList<Integer> intChecksToPerform = new ArrayList<>();
            for (String checkToPerform : splitChecksToPerform){
                intChecksToPerform.add(Integer.parseInt(checkToPerform) - 1);
            }
            ArrayList<CheckType> checksToPerformTypes = new ArrayList<>();
            for (Integer checkToPerformInt : intChecksToPerform){
                checksToPerformTypes.add(this.availibleChecks.get(checkToPerformInt));
            }
            UserOptions userOptions = getUserOptions(checksToPerformTypes, reader);
            System.out.println("Please enter the full file path to the directory which contains the class files you would like to lint?");
            System.out.println("Notice: all class files in given directory will be linted, if you only want to lint a subset use a more specific directory.");
            String filepath = reader.readLine();
            ArrayList<PresentationInformation> presentationInformations = (ArrayList<PresentationInformation>) linter.runChecks(checksToPerformTypes, filepath, userOptions);
            this.displayResults(presentationInformations);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserOptions getUserOptions(ArrayList<CheckType> checksToPerformTypes, BufferedReader reader) throws IOException {
        UserOptions userOptions = new UserOptions();
        setMaximumMethods(checksToPerformTypes, reader, userOptions);
        setNamingConventionAutoCorrect(checksToPerformTypes, reader, userOptions);
        setParseUml(reader, userOptions);
        return userOptions;
    }

    private void setParseUml(BufferedReader reader, UserOptions userOptions) throws IOException {
        System.out.println("Would you like to generate a uml? (yes or no)");
        if (reader.readLine().equalsIgnoreCase("yes")){
            userOptions.parseUml = true;
            System.out.println("What is the directory you would like the uml image and text to be outputted to? Please enter the full file path.");
            userOptions.umlOutputDirectory = reader.readLine();
        }else{
            userOptions.parseUml = false;
        }
    }

    private void setMaximumMethods(ArrayList<CheckType> checksToPerformTypes, BufferedReader reader, UserOptions userOptions) throws IOException {
        if (checksToPerformTypes.contains(CheckType.SingleResponsibilityPrinciple)) {
            System.out.println("You have selected to check for the Single Responsibility Principle, what would you like to set as your maximum amount of public methods?");
            System.out.println("You may enter a number or simply enter \"default\"");
            String maximumMethods = reader.readLine();
            if (maximumMethods.toLowerCase().trim().equals("default")) {
                userOptions.maximumMethods = -1;
            } else {
                userOptions.maximumMethods = Integer.parseInt(maximumMethods);
            }
        }
    }

    private void setNamingConventionAutoCorrect(ArrayList<CheckType> checksToPerformTypes, BufferedReader reader, UserOptions userOptions) throws IOException {
        if (checksToPerformTypes.contains(CheckType.PoorNamingConvention)) {
            System.out.println("You have selected to check that your project follows Standard Naming Conventions, would you like to autocorrect the names of classes/fields/methods?");
            System.out.println("Please enter yes or no");
            String stnReader = reader.readLine();
            if (stnReader.toLowerCase().trim().equals("yes")) {
                userOptions.namingConventionAutoCorrect = true;
            } else if (stnReader.toLowerCase().trim().equals("no")) {
                userOptions.namingConventionAutoCorrect = false;
            } else {
                System.out.println("Invalid input, autocorrect is not enabled");
                userOptions.namingConventionAutoCorrect = false;
            }
        }
    }

    private void displayResults(ArrayList<PresentationInformation> presentationInformations){
        for (PresentationInformation presentationInformation : presentationInformations){
            if (presentationInformation.passed){
                System.out.println(presentationInformation.checkName.toString() + " passed!");
            }else{
                System.out.println(presentationInformation.checkName.toString() + " failed!");
            }
            for (String displayline : presentationInformation.displayLines){
                System.out.println("      " + displayline);
            }
        }
    }
}
