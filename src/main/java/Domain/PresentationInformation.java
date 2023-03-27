package Domain;

import java.util.ArrayList;
import java.util.List;

public class PresentationInformation {
    public boolean passed;
    public CheckType checkName;
    public List<String> displayLines;

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
}
