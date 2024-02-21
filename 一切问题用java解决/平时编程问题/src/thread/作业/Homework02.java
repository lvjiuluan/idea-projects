package thread.作业;

public class Homework02 {
    public static void main(String[] args) {
        Card card = new Card();
        new Thread(card).start();
        new Thread(card).start();
    }
}

class Card implements Runnable {

    private int money = 100 * 1000;

    private int count = 0;

    @Override
    public void run() {
        // 取钱
        while (true) {
            synchronized (this) {
                if (money < 1000) {
                    System.out.println("余额不足...");
                    break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                money -= 1000;
                System.out.println(String.format("%s第%d次取钱, 剩余%d元",
                        Thread.currentThread().getName(),
                        ++count,
                        money
                ));
            }
        }
    }
}
