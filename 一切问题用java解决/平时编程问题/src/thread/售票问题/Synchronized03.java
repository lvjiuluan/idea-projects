package thread.售票问题;

public class Synchronized03 {
    public static void main(String[] args) {
        SynchronizedSellTicket03 t01 = new SynchronizedSellTicket03();
        SynchronizedSellTicket03 t02 = new SynchronizedSellTicket03();
        SynchronizedSellTicket03 t03 = new SynchronizedSellTicket03();
        t01.start();
        t02.start();
        t03.start();
        /*
         *
         * 这段代码还是会出现超卖现象，因为synchronized是一个非静态成员方法
         * 等价与 synchronized (this){}
         * 而这里我new了三个对象，每个对象的this不一样，加锁就失去了意义
         * 所以会超卖
         * */

    }
}

class SynchronizedSellTicket03 extends Thread {

    private static int number = 100;

    private static boolean loop = true;

    public static synchronized void sell() { //同步方法的方式，同一时刻，只能有一个线程执行sell方法
        if (number <= 0) {
            System.out.println("售票结束...");
//            setLoop(false);
            loop = false;
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
