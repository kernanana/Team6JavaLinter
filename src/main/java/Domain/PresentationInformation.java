package Domain;

import Domain.Checks.Check;

import java.util.ArrayList;
import java.util.List;

public class PresentationInformation {
    private boolean passed;
    private CheckType checkName;
    private List<String> displayLines;

    public PresentationInformation(CheckType checkName) {
        reinitialize(checkName);
    }

    public boolean reinitialize(CheckType check){
        checkName = check;
        passed = false;
        displayLines = new ArrayList<>();
        return true;
    }
    public boolean resetPI(){
        passed = false;
        displayLines = new ArrayList<>();
        return true;
    }
    public int countDisplayLines(){
        return displayLines.size();
    }

    public boolean isPresentingCheck(CheckType check){
        return checkName.equals(check);
    }

    public String returnUIMessage(){
        if(passed){
            return checkName + " passed!";
        }
        else{
            return checkName + " failed!";
        }
    }

    public void addDisplayLine(String displayLine) {
        displayLines.add(displayLine);
    }

    public void removeLastNDisplayLines(int numberToRemove) {
        int start = displayLines.size()-numberToRemove;
        if (numberToRemove > start) {
            displayLines.subList(start, numberToRemove).clear();
        }
    }

    public void passedCheck() {
        passed = true;
    }

    public void failedCheck() {
        passed = false;
    }

    public String printToDisplay() {
        StringBuilder text = new StringBuilder();
        for (String displayLine : displayLines){
             text.append("      ").append(displayLine).append("\n");
        }
        return text.toString();
    }

    public boolean hasPassed() {
        return passed;
    }

    public List<String> getDisplayLines() {
        return displayLines;
    }

    public CheckType getCheckName() {
        return checkName;
    }
}
