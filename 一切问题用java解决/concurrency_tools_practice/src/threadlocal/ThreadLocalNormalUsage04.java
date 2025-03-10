package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *
 *  加锁来解决线程安全问题
 * */
public class ThreadLocalNormalUsage04 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int j = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String data = new ThreadLocalNormalUsage04().data(j);
                    System.out.println(data);
                }
            });
        }
        threadPool.shutdown();
    }

    public String data(int seconds) {
        // 参数是毫秒，从1970-1-1 00:00:00开始计算，经过了多少毫秒，然后转换成现在的日期
        Date date = new Date(1000 * seconds);
        String s;
        synchronized (ThreadLocalNormalUsage04.class) {
            s = simpleDateFormat.format(date);
        }
        return s;
    }
}
