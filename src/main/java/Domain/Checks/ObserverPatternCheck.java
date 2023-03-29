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
    public PresentationInformation check(CheckData data) {
        PresentationInformation presentationInformation = new PresentationInformation(CheckType.ObserverPattern);
        this.classes = data.getClasses();

        for (ClassAdapter classAdapter : classes){
            boolean isAbstract = classAdapter.getIsAbstract();
            boolean isInterface = classAdapter.getIsInterface();
            if(isAbstract || isInterface) {
                subject = classAdapter;
                checkFieldsForPattern(presentationInformation, classAdapter);
            }
        }

        return presentationInformation;
    }

    private void checkFieldsForPattern(PresentationInformation presentationInformation, ClassAdapter classAdapter) {
        for (FieldAdapter fieldAdapter : classAdapter.getAllFields()){
            if(fieldAdapter.isList()) {
                String fieldType = fieldAdapter.getCollectionType();
                observer = findObserver(fieldType);
                if (observer != null) {
                    findConcreteImplementations(presentationInformation);
                }
            }
        }
    }

    private void findConcreteImplementations(PresentationInformation presentationInformation) {
        for (ClassAdapter classNode : classes) {
            findConcreteObserver(classNode);
            findConcreteSubject(classNode);
        }
        if (concObserver != null && concSubject != null) {
            presentationInformation.passedCheck();
            presentationInformation.addDisplayLine("Observer Pattern has been detected: \n"
                    + "\t Subject: " + subject.getClassName() + " | Observer: " + observer.getClassName() + "\n"
                    + "\t Concrete Subject: " + concSubject.getClassName() + " | Concrete Observer: " + concObserver.getClassName());
        }
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
