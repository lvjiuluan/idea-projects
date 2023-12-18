package 韩顺平Java.单例模式与final;

public class FinalTest {
    public static void main(String[] args) {
        Parent parent = new Child();
        Child child = new Child();
        parent.sayHello();
        child.sayHello();
    }
}
class A{
    public static int a = 0;
    public final int b = 1;
    public static final int C = 2;
    static {
        System.out.println("类加载了....");
    }
}

class Parent {
    public static void sayHello() {
        System.out.println("Hello from Parent");
    }
}

class Child extends Parent {
//    public static void sayHello() {
//        System.out.println("Hello from Child");
//    }
}

