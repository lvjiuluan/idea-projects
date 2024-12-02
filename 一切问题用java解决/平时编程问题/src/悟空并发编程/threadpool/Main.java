package 悟空并发编程.threadpool;

public class Main {
}

class Father {
    int f1;
    float f2;

    public Father(int f1) {
        this.f1 = f1;
    }

    public Father(int f1, float f2) {
        this.f2 = f2;
    }
}

class Son extends Father {
    int s1;

    public Son(int s1) {
        super(0);
        this.s1 = s1;
    }
}