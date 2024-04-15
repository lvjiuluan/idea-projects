package 每周20道力扣.第六周;

public class ThreadStatus {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new T());
        System.out.println(thread.getState()); // New

        thread.start();
        System.out.println(thread.getState()); // Runnable

        Thread.sleep(1000);
        System.out.println(thread.getState()); // Terminated
    }
}

class T implements Runnable {

    @Override
    public void run() {
        /*synchronized (this) {
            System.out.println(Thread.currentThread().getState());
            try {
                this.wait();
                System.out.println(Thread.currentThread().getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
