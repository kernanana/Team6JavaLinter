package Presentation;

import Domain.CheckType;
import Domain.Checks.Check;
import Domain.Linter;
import Domain.UserOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class UI {
    protected Linter linter;
    public List<CheckType> availableChecks;
    protected final Map<CheckType, Check> checkComposition;
    protected Map<String, Object> config;

    public UI (Linter linter, Map<String, Object> config){
        this.linter = linter;
        this.config = config;
        availableChecks = new ArrayList<>();
        checkComposition = new HashMap<>();
        initializeAvailableChecks();
        linter.defineAvailableChecks(checkComposition);
    }
    public abstract void UIMain();
    protected abstract void initializeAvailableChecks();

    protected void registerCheck(CheckType checkType, Check check) {
        availableChecks.add(checkType);
        checkComposition.put(checkType, check);
    }

    protected abstract UserOptions getUserOptions(BufferedReader reader) throws IOException;
}
