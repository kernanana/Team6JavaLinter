package ASMPracticeCode.ObserverDummyData;

public class ObserveeSub3 extends Observee{
    protected ObserveeSub3(int n) {
        super(n);
    }

    @Override
    public int update() {
        return num = num-3;
    }
}
