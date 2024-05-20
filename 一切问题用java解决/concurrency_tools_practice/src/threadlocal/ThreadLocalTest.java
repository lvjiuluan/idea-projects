package threadlocal;

public class ThreadLocalTest {
    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + ": " + ThreadSafeFormatter.dateFormatThreadLocal.get());

        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + ThreadSafeFormatter.dateFormatThreadLocal.get());
        });
        thread.start();
    }
}
