package ASMPracticeCode;

public class SimpleClass {
    private String hello;
    public SimpleClass(){
        this.hello = "hello";
    }

    public void printName(String name) {
         String temp = hello + " " + name;
         System.out.println(temp);
    }

}
