package ASMPracticeCode.NamingConventionDummyData;

class MyClassLoader extends ClassLoader{
    public MyClassLoader(){};
    public Class defineClass(String name, byte[] b){
        return defineClass(name, b,0,b.length);
    }
}