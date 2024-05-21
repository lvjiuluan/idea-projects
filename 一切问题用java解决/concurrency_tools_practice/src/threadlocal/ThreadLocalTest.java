package threadlocal;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalTest {
    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + ": " + ThreadSafeFormatter.dateFormatThreadLocal.get());

        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + ThreadSafeFormatter.dateFormatThreadLocal.get());
        });
        thread.start();
    }
}
class ThreadSafeFormatter1 {
    private static AtomicInteger counter = new AtomicInteger(0);

    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                @Override
                public String toString() {
                    return super.toString() + ":" + counter.getAndIncrement();
                }
            };
            System.out.println(Thread.currentThread().getName() + "创建对象" + sdf);
            return sdf;
        }
    };
}
