package thread.method;

public class Thread01 {
    public static void main(String[] args) throws InterruptedException {
        T t = new T();
        t.start();
        for (int i = 0; i < 20; i++) {
            Thread.sleep(1000);
            System.out.println("主线程(小弟)吃了 " + i + " 个包子");
            if (i == 5) {
                System.out.println("主线程(小弟)让子线程(老大)先吃");
//                t.join();
                Thread.yield();
            }
        }
    }
}

class T extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程(老大)吃了 " + i + " 个包子");
        }
    }
}
