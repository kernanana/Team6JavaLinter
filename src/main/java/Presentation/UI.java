package Presentation;

import Domain.CheckType;
import Domain.Linter;

import java.util.List;

public abstract class UI {
    Linter linter;
    List<CheckType> availibleChecks;

    public UI (Linter linter, List<CheckType> availibleChecks){
        this.linter = linter;
        this.availibleChecks = availibleChecks;
    }
    public abstract void UIMain();
}
