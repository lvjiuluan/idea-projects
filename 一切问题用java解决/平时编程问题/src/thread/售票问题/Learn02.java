package thread.售票问题;

public class Learn02 {
}

class A {
    public static void syn() {
        synchronized (A.class) {
            System.out.println("sss");
        }
    }
}
