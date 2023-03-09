package ASMPracticeCode;

public abstract class AbstractClass {
    protected int total;
    public final int templateMethod(){
        total += primitiveOperation1();
        total += primitiveOperation2();
        return total;
    }

    public abstract int primitiveOperation1();
    public abstract int primitiveOperation2();
}

