package ASMPracticeCode;

import java.util.ArrayList;

public class ConcreteClass1 extends AbstractClass{
    private ArrayList<String> strings;
    @Override
    public int primitiveOperation1() {
        return 5;
    }

    @Override
    public int primitiveOperation2() {
        return 5;
    }

    public boolean equals(Object o){
        return true;
    }
}
