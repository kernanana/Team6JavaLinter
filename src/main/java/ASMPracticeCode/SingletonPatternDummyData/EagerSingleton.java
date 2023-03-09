package ASMPracticeCode.SingletonPatternDummyData;

public class EagerSingleton {
    private static EagerSingleton instance = new EagerSingleton();

    private EagerSingleton(){}

    static public EagerSingleton getInstance(){
        return instance;
    }
}
