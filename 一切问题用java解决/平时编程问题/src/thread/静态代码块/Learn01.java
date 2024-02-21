package thread.静态代码块;

public class Learn01 {
    public static void main(String[] args) {
        int i = A.i;
        A a = new A();
    }
}

class A {
    static int i = 0;
    static int z = 1;
    int j;
    int k = 0;

    public A() {
        System.out.println("...");
    }
}
