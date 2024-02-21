package thread.线程创建;

public class Thread03 {
    public static void main(String[] args) {
        Pig pig = new Pig();
    }
}

class Pig {
    int i = 0;

    Pig() {
        System.out.println("...");
    }
}
