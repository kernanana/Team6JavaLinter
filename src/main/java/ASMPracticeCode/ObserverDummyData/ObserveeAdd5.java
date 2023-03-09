package ASMPracticeCode.ObserverDummyData;

public class ObserveeAdd5 extends Observee {

    protected ObserveeAdd5(int n) {
        super(n);
    }
    @Override
    public int update() {
        num = num + 5;
        return 0;
    }
}
