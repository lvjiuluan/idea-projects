package thread.线程创建;

public class Thread04 {
    public static void main(String[] args) {
        T1 t1 = new T1();
        T2 t2 = new T2();
        Thread thread01 = new Thread(t1);
        Thread thread02 = new Thread(t2);
        thread01.start();
        thread02.start();
    }
}

class T1 implements Runnable {

    int count = 0;

    @Override
    public void run() {
        while (count < 100) {
            System.out.println("hello world " + (++count));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class T2 implements Runnable {

    int count = 0;

    @Override
    public void run() {
        while (count < 50) {
            System.out.println("hi " + (++count));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}