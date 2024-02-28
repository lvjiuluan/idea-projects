package 韩顺平Java.字符串综合.多线程执行;

public class String01 {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
//        StringBuffer sb = new StringBuffer();
        T t = new T(sb);
        for (int i = 0; i < 5; i++) {
            new Thread(t).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sb.length());
    }
}

class T implements Runnable {

    StringBuilder sb;

    public T(StringBuilder sb) {
        this.sb = sb;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            sb.append('a');
        }
    }
}
/*
*
* 如果使用StringBuilder类
* 线程1 运行append时
* 线程2 也会进入append方法，此时线程2读取了线程1还没有写如的结果
* 所以会造成错误。解决办法是把append方法加上synchronized。
* 在任意一个时刻，只有一个线程在执行append方法，当结果写入sb中，才会结束，
* 然后释放锁，其它方法来抢占StringBuffer的锁
*
* */

/*
 *每一个线程都要new StringBuilder()在自己的局部线程栈中，其它线程不知道这个对象的地址
 * 所以这块内存是线程安全的，所以这个操作是线程安全。
 *
 *
 *
 *
 *
 * */
