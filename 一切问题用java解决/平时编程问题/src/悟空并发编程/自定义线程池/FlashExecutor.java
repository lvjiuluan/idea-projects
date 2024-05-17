package 悟空并发编程.自定义线程池;

public class FlashExecutor implements Executor {
    @Override
    public void execute(Runnable r) {
        new Thread(r).start();
    }
}
