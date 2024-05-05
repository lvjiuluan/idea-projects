package 韩顺平Java.并发编程;

public class VolatileTest01 {
    private static boolean flag = false;  // 没有volatile修饰

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);  // 让主线程先运行，确保主线程能进入循环
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                flag = true;  // 子线程修改flag的值
                System.out.println("Flag updated to true");
            }
        }).start();

        while (!flag) {
            // 主线程在这个循环中检测flag的值
        }
        System.out.println("Main thread sees flag = true");
    }
}
