package thread.线程退出;

public class ThreadExit {
    public static void main(String[] args) {
        T t1 = new T();
        t1.start();

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 通知t1线程退出
        t1.setLoop(false);
    }
}

class T extends Thread {

    private int count = 0;

    // 设置一个控制变量
    private boolean loop = true;

    @Override
    public void run() {
        while (loop) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // InterruptedException 这里catch InterruptedException异常
                // 当其它线程调用该线程的interrupt方法时会执行到这里面
                // interrupt是捕获到了一个中断异常、不是中止
                e.printStackTrace();
            }
            System.out.println(String.format("%d 线程T还在运行中...", ++count));
        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}
