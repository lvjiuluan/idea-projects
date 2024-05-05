package 悟空并发编程.syn;

public class ShowUnsafe1 implements Runnable {

    int a;

    private void increase() {
        a++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            increase();
        }
    }

    public static void main(String[] args) {
        ShowUnsafe1 showUnsafe1 = new ShowUnsafe1();
        Thread thread1 = new Thread(showUnsafe1);
        Thread thread2 = new Thread(showUnsafe1);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(showUnsafe1.a);
    }
}
