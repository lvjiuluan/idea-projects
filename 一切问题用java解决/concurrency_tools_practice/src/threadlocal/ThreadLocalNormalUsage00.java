package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalNormalUsage00 {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = new ThreadLocalNormalUsage00().data(10);
                System.out.println(data);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = new ThreadLocalNormalUsage00().data(1007);
                System.out.println(data);
            }
        }).start();
    }

    public String data(int seconds) {
        // 参数是毫秒，从1970-1-1 00:00:00开始计算，经过了多少毫秒，然后转换成现在的日期
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
