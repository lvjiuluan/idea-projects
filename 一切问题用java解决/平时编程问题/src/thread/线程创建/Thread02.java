package thread.线程创建;

public class Thread02 {
    public static void main(String[] args) {
//        Dog dog = new Dog();
//        Thread thread = new Thread(dog);
//        thread.start();
//
//        int times = 0;
//        while (times < 80) {
//            times++;
//            System.out.println(Thread.currentThread().getName() + ": 汪汪，我是狗狗 \t" + times);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        Tiger tiger = new Tiger();
        Proxy proxy = new Proxy(tiger);
        proxy.start();

    }
}

class Tiger implements Runnable {

    @Override
    public void run() {
        System.out.println("老虎嗷嗷叫...");
    }
}

class Proxy implements Runnable {  // 把Proxy当作Thread类

    private Runnable target = null;

    public Proxy(Runnable target) {
        this.target = target;
    }

    public void start() {
        start0();
    }

    public void start0() {
        run();
    }

    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }
}

class Dog implements Runnable {

    int times = 0;

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + ": 汪汪，我是狗狗 \t" + (++times));
            // 让该线程休眠1秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (times == 800) {
                break;
            }
        }
    }
}