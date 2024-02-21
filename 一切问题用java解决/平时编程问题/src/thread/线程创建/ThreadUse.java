package thread.线程创建;

public class ThreadUse {
    public static void main(String[] args) {
        Cat cat01 = new Cat();
        // 启动线程, 运行一次run方法
        // 1.start方法
        // 2.调用start0  private native void start0();  start0是native的
        // 3. 由本地语言实现是native方法，由JVM调用
        cat01.start();
//        cat01.run();
        int times = 0;
        while (times < 80) {
            times++;
            System.out.println(Thread.currentThread().getName() + ": 喵喵，我是猫猫 \t" + times);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// 重写run方法，这个run方法本源是来自于Runnable接口
// 在run方法里面写上自己的业务代码
class Cat extends Thread {
    int times = 0;

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + ": 喵喵，我是猫猫 \t" + (++times));
            // 让该线程休眠1秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (times == 800) {
                break;
            }
        }
    }

}