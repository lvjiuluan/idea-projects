package threadlocal.不同副本对象;

public class ThreadLocalNPE {
    ThreadLocal<Long> longThreadLocal = new ThreadLocal<>();

    public void set() {
        longThreadLocal.set(Thread.currentThread().getId());
    }

    public Long get() {
        return longThreadLocal.get();
    }

    public static void main(String[] args) {
      /*  ThreadLocalNPE threadLocalNPE = new ThreadLocalNPE();
        threadLocalNPE.set();
        System.out.println(threadLocalNPE.get());
        Thread thread1 = new Thread(() -> {
//            threadLocalNPE.set();
            System.out.println(threadLocalNPE.get());
        });
        thread1.start();*/

        Integer I = null;
        int i = I; // 调用I.intValue，由于I是null，所以会报空指针异常
    }
}
