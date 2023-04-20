package DataSource;

public class ConcreteClassLoader extends ClassLoader{
    public ConcreteClassLoader(){};

    public Class defineClass(String name, byte[] b){
        return defineClass(name, b,0,b.length);
    }
}
