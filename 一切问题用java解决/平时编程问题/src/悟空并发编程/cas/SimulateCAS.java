package 悟空并发编程.cas;

/*
 *
 * 模拟CAS操作，等价代码
 *
 * */
public class SimulateCAS {
    private volatile int value;

    public synchronized int compareAndSwap(int expectedVale, int newValue) {
        int oldValue = value;
        if (oldValue == expectedVale) {
            value = newValue;
        }
        return oldValue;
    }
}
