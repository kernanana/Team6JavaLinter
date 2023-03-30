package Presentation;

import Domain.CheckType;
import Domain.Checks.*;
import Domain.Linter;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConsoleUI extends UI {

    public ConsoleUI(Linter linter, Map<String, Object> config){
        super(linter, config);
    }

    protected void initializeAvailableChecks() {
        registerCheck(CheckType.PoorNamingConvention, new NamingConventionCheck());
        registerCheck(CheckType.EqualsHashCode, new EqualsHashCodeCheck());
        registerCheck(CheckType.SingleResponsibilityPrinciple, new SingleResponsibilityPrincipleCheck());
        registerCheck(CheckType.InformationHidingViolation, new InformationHidingCheck());
        registerCheck(CheckType.SingletonPattern, new SingletonPatternCheck());
        registerCheck(CheckType.DecoratorPattern, new DecoratorPatternCheck());
        registerCheck(CheckType.StrategyPattern, new StrategyPatternCheck());
        registerCheck(CheckType.DataClass, new DataClassCheck());
        registerCheck(CheckType.ObserverPattern, new ObserverPatternCheck());
        registerCheck(CheckType.RedundantInterface, new RedundantInterface());
    }

    protected void registerCheck(CheckType checkType, Check check) {
        availableChecks.add(checkType);
        checkComposition.put(checkType, check);
    }

    public void UIMain() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("What checks would you like to perform?");
        for (int i = 0; i < availableChecks.size(); i++){
            System.out.println((i + 1) + ". " + availableChecks.get(i).toString());
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
                checksToPerformTypes.add(availableChecks.get(checkToPerformInt));
            }
            UserOptions userOptions = getUserOptions(reader);
            System.out.println("Please enter the full file path to the directory which contains the class files you would like to lint?");
            System.out.println("Notice: all class files in given directory will be linted, if you only want to lint a subset use a more specific directory.");

            String filepath;
            if(config.containsKey("directory"))
                filepath = (String) config.get("directory");
            else
                filepath = reader.readLine();

            ArrayList<PresentationInformation> presentationInformations = (ArrayList<PresentationInformation>) linter.runChecks(checksToPerformTypes, filepath, userOptions);
            this.displayResults(presentationInformations);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected UserOptions getUserOptions(BufferedReader reader) throws IOException {
        UserOptions userOptions = new UserOptions();

        if (availableChecks.contains(CheckType.SingleResponsibilityPrinciple)){
            System.out.println("If you have selected the Single Responsibility Principle, what would you like to set as your maximum amount of public methods?");
            System.out.println("You may enter a number or simply enter \"default\"");
            String maximumMethods = reader.readLine();
            if (!maximumMethods.toLowerCase().trim().equals("default")){
                userOptions.defineMaxMethods(Integer.parseInt(maximumMethods));
            }
        }

        if(availableChecks.contains(CheckType.PoorNamingConvention)){
            System.out.println("If you have selected Standard Naming Conventions, would you like to autocorrect the names of classes/fields/methods?");
            System.out.println("Please enter yes or no");
            String stnReader = reader.readLine();
            if(stnReader.toLowerCase().trim().equals("yes")){
                userOptions.doAutoCorrect();
            } else if (!stnReader.toLowerCase().trim().equals("no")) {
                System.out.println("Invalid input, autocorrect is not enabled");
            }
        }

        System.out.println("Would you like to generate output in a txt file? (yes or no)");
        if(reader.readLine().toLowerCase().equals("yes")){
            userOptions.saveOutput("");
        }

        System.out.println("Would you like to generate a uml? (yes or no)");
        if (reader.readLine().toLowerCase().equals("yes")){
            System.out.println("What is the directory you would like the uml image and text to be outputted to? Please enter the full file path.");
            userOptions.doUMLParse(reader.readLine());
        }

        return userOptions;
    }

    private void displayResults(ArrayList<PresentationInformation> presentationInformations){
        for (PresentationInformation presentationInformation : presentationInformations){
                System.out.println(presentationInformation.returnUIMessage());
                System.out.println(presentationInformation.printToDisplay());
        }
    }
}
