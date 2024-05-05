package 韩顺平Java.并发编程;

public class MyThreadTest {
}

class MyThread implements Runnable {
    private Runnable target;

    public MyThread(Runnable target) {
        this.target = target;
    }

    public MyThread() {
    }


    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }

    public void start() {
        start0();
    }

    private native void start0();
}
