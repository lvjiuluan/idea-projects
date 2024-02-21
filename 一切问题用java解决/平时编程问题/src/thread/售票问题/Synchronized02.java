package thread.售票问题;

public class Synchronized02 {
    public static void main(String[] args) {
        SynchronizedSellTicket02 syn = new SynchronizedSellTicket02();
        new Thread(syn).start();
        new Thread(syn).start();
        new Thread(syn).start();
    }
}

class SynchronizedSellTicket02 implements Runnable {

    private int number = 100;

    private boolean loop = true;

    public synchronized void sell() { //同步方法的方式，同一时刻，只能有一个线程执行sell方法
        if (number <= 0) {
            System.out.println("售票结束...");
            setLoop(false);
            return;
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("窗口" + Thread.currentThread().getName() +
                "售出一张票, 剩余票数=" + (--number) + ", 优先级 = " + Thread.currentThread().getPriority());

    }

    @Override
    public void run() {
        while (loop) {
            sell();
        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}