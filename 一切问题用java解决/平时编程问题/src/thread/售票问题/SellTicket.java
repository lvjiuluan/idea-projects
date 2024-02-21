package thread.售票问题;

/*
 *
 * 使用多线程模拟三个窗口同时售票
 * 票有100张
 *
 * */
public class SellTicket {
    public static void main(String[] args) {
        /*SellTicket01 sellTicket011 = new SellTicket01();
        SellTicket01 sellTicket012 = new SellTicket01();
        SellTicket01 sellTicket013 = new SellTicket01();
        sellTicket011.start();
        sellTicket012.start();
        sellTicket013.start();*/
        SellTicket02 sellTicket02 = new SellTicket02();
        Thread thread01 = new Thread(sellTicket02);
        Thread thread02 = new Thread(sellTicket02);
        Thread thread03 = new Thread(sellTicket02);
        thread01.start();
        thread02.start();
        thread03.start();

    }
}
/*
*
* 窗口Thread-2售出一张票，剩余票数=2
售票结束...
窗口Thread-0售出一张票，剩余票数=0
售票结束...
窗口Thread-1售出一张票，剩余票数=1
售票结束...

* */

/*
 * 使用继承Thread类方法
 * */
class SellTicket01 extends Thread {

    // 让多个线程共享number
    private static int number = 100;

    @Override
    public void run() {
        while (number >= 1) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("窗口" + Thread.currentThread().getName() + "售出一张票，剩余票数=" + (--number));
        }
        System.out.println("售票结束...");
    }
}

class SellTicket02 implements Runnable {

    private int number = 100;

    @Override
    public void run() {
        while (number >= 1) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("窗口" + Thread.currentThread().getName() +
                    "售出一张票, 剩余票数=" + (--number)+ ", 优先级 = "+Thread.currentThread().getPriority());
        }
        System.out.println("售票结束...");
    }
}