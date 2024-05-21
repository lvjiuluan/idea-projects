package threadlocal;

public class ValueLeakage {
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("对象被回收...");
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set(new ValueLeakage());
        threadLocal.remove();
        threadLocal = null;
//        System.gc();
        Thread.sleep(1000);
    }
}
