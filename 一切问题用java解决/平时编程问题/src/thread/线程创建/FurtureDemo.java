package thread.线程创建;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FurtureDemo {
    public static void main(String[] args) {
        T t = new T();
        FutureTask<String> futureTask = new FutureTask<>(t);
        String res = null;

        Thread thread = new Thread(futureTask, "线程1");
        thread.start();
        try {
            res = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }
}

class T implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "hello world";
    }
}
