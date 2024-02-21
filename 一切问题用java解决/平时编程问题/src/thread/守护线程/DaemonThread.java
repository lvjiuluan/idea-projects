package thread.守护线程;

public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        MyDaemonThread myDaemonThread = new MyDaemonThread();
        myDaemonThread.setDaemon(true);
        myDaemonThread.start();
        // 将myDaemonThread设置成守护线程
        // 用户线程结束后，可以自动结束
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println("宝强在辛苦的工作...");
        }
    }
}

class MyDaemonThread extends Thread {

    @Override
    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("马蓉和宋喆聊天...");
        }
    }
}
