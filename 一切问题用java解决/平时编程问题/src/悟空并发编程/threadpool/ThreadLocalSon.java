package 悟空并发编程.threadpool;

import java.text.SimpleDateFormat;

public class ThreadLocalSon extends ThreadLocal<SimpleDateFormat>{
    @Override
    protected SimpleDateFormat initialValue() {
        return super.initialValue();
    }
}
