package ASMPracticeCode.ObserverDummyData;

public abstract class Observee {
    protected int num;

    protected Observee(int n){
        num = n;
    }

    public abstract int update();

    public int display(){
        System.out.println(num);
        return num;
    }
}
