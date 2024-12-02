package 悟空并发编程.threadpool;

import com.sun.org.apache.bcel.internal.generic.ARETURN;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class SimpleDataFormatDemo01 {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.execute(()->{
                System.out.println(date(finalI));
            });
        }
    }

    public static String date(int seconds){
        // 从 1970-01-01 00:00:00 开始计时，GMT时间
        // 中国是东八区，等于GMT计时 + 8
        Date date = new Date(seconds * 1000);
        SimpleDateFormat sdf = ThreadSafeSimpleDateFormat.simpleDateFormatThreadLocal.get();
        String format = sdf.format(date);
        return format;
    }
}

class ThreadSafeSimpleDateFormat{
    public static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>(){
        // 通过匿名内部类的方式创建ThreadLocal对象
        // 并重写initValue方法
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        }
    };
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal =
            ThreadLocal.withInitial(new Supplier<SimpleDateFormat>() {
                @Override
                public SimpleDateFormat get() {
                    return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                }
            });
    public static void test(){
        simpleDateFormatThreadLocal.remove();
    }
}