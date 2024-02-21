package thread.线程不安全;

public class IPlusPlus {
    public static void main(String[] args) throws InterruptedException {
        T t = new T();
        new Thread(t).start();
        new Thread(t).start();
        new Thread(t).start();
    }
}

class T implements Runnable {

    int i = 0;


    @Override
    public void run() {
        System.out.println("线程 " + Thread.currentThread().getName() + "获取到的值是:" + i);
        i++;
    }
}
