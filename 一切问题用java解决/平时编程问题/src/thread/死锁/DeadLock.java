package thread.死锁;

public class DeadLock {
    public static void main(String[] args) throws InterruptedException {
        DeadLockDemo t01 = new DeadLockDemo(true);
        DeadLockDemo t02 = new DeadLockDemo(false);
        t01.start();
        t02.start();
        for (int i = 0; i < 10; i++) {
            System.out.println(t01.getName() + ":" + t01.getState());
            System.out.println(t02.getName() + ":" + t02.getState());
            Thread.sleep(1000);
        }
    }
}

class DeadLockDemo extends Thread {
    static Object o1 = new Object();
    static Object o2 = new Object();
    boolean flag;

    public DeadLockDemo(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag) {
            synchronized (o1) {
                System.out.println(Thread.currentThread().getName() + "进入1");
                try {
                    o1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println(Thread.currentThread().getName() + "进入2");
                }
            }
        } else {
            synchronized (o2) {
                System.out.println(Thread.currentThread().getName() + "进入3");
                synchronized (o1) {
                    System.out.println(Thread.currentThread().getName() + "进入4");
                    o1.notify();
                }

            }
        }
    }
}
