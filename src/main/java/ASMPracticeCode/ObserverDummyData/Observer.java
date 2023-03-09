package ASMPracticeCode.ObserverDummyData;

import java.util.ArrayList;

public class Observer {
    private ArrayList<Observee> observees = new ArrayList<Observee>();

    public Observer(){
    }

    public boolean add(Observee o){
        observees.add(o);
        return true;
    }

    public boolean remove(Observee o){
        observees.remove(o);
        return true;
    }

    public boolean updateAll(){
        for(int i = 0; i < observees.size(); i++){
            observees.get(i).update();
        }
        return true;
    }

    public boolean displayAll(){
        for(int i = 0; i < observees.size(); i++){
            observees.get(i).display();
        }
        return true;
    }
}
