package thread.售票问题;

public class Synchronized01 {
    public static void main(String[] args) {
        SynchronizeSellTicket01 thread01 = new SynchronizeSellTicket01();
        SynchronizeSellTicket01 thread02 = new SynchronizeSellTicket01();
        SynchronizeSellTicket01 thread03 = new SynchronizeSellTicket01();
        thread01.start();
        thread02.start();
        thread03.start();
    }
}

class SynchronizeSellTicket01 extends Thread {

    // 让多个线程共享number
    private static Integer number = 100;

    private static Object lock = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                if (number <= 0) {
                    System.out.println("售票结束...");
                    break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("窗口" + Thread.currentThread().getName() + "售出一张票，剩余票数=" + (--number));
            }
        }
    }
}