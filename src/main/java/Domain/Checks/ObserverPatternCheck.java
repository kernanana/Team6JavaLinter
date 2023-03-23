package Domain.Checks;

import Domain.Adapters.ClassAdapter;
import Domain.Adapters.FieldAdapter;
import Domain.Adapters.MethodAdapter;
import Domain.CheckType;
import Domain.PresentationInformation;
import Domain.UserOptions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObserverPatternCheck implements Check{

    private MethodAdapter observerMethod;
    private List<ClassAdapter> classes;
    private ClassAdapter subject = null;
    private ClassAdapter observer = null;
    private ClassAdapter concSubject = null;
    private ClassAdapter concObserver = null;

    @Override
    public PresentationInformation check(List<ClassAdapter> classes, UserOptions userOptions) {
        PresentationInformation presentationInformation = new PresentationInformation();
        presentationInformation.checkName = CheckType.ObserverPattern;
        ArrayList<String> displayLines = new ArrayList<>();
        presentationInformation.passed = false;
        this.classes = classes;


        for (ClassAdapter classAdapter : classes){
            boolean isAbstract = classAdapter.getIsAbstract();
            boolean isInterface = classAdapter.getIsInterface();
            if(isAbstract || isInterface) {
                subject = classAdapter;
                for (FieldAdapter fieldAdapter : classAdapter.getAllFields()){
                    if(fieldAdapter.isList()) {
                        String fieldType = fieldAdapter.getCollectionType();
                        observer = findObserver(fieldType);
                        if (observer != null) {
                            for (ClassAdapter classNode : classes) {
                                findConcreteObserver(classNode);
                                findConcreteSubject(classNode);
                            }
                            if (concObserver != null && concSubject != null) {
                                presentationInformation.passed = true;
                                displayLines.add("Observer Pattern has been detected: \n"
                                        + "\t Subject: " + subject.getClassName() + " | Observer: " + observer.getClassName() + "\n"
                                        + "\t Concrete Subject: " + concSubject.getClassName() + " | Concrete Observer: " + concObserver.getClassName());
                            }
                        }
                    }
                }
            }
        }
        presentationInformation.displayLines = displayLines;
        return presentationInformation;
    }

    private void findConcreteSubject(ClassAdapter classNode) {
        if(classNode.getInterfaces().contains(subject.getClassName()) || classNode.getExtends().contains(subject.getClassName()))
            concSubject = classNode;
    }

    private ClassAdapter findObserver(String observerName) {
        for(ClassAdapter potentialObserver : classes) {
            if(potentialObserver.getIsInterface() && observerName.contains(potentialObserver.getClassName())) {
                if(potentialObserver.getAllMethods().size() == 1) {
                    if(potentialObserver.getAllMethods().get(0).getIsAbstract())
                        observerMethod = potentialObserver.getAllMethods().get(0);
                    return potentialObserver;
                }
            }
        }
        return null;
    }

    private void findConcreteObserver(ClassAdapter classNode) {
        if(classNode.getInterfaces().contains(observer.getClassName())) {
            for(FieldAdapter field : classNode.getAllFields()) {
                if (field.getType().contains(subject.getClassName())) {
                    for (MethodAdapter method : classNode.getAllMethods()) {
                        if (!method.getIsAbstract() && method.getMethodName().equals(observerMethod.getMethodName()))
                            concObserver = classNode;
                    }
                }
            }
        }
    }

}
