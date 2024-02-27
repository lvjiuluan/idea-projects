package 韩顺平Java.接口.多态传递;

public class C implements A {
    public static void main(String[] args) {
        A a = new C();
        B b = new C();
    }
}
