package 韩顺平Java.集合02.mapThread;

import java.util.HashMap;
import java.util.Map;

public class Map01 {
    public static void main(String[] args) {
        Map map = new HashMap<>();
        T t = new T(map);
        for (int i = 0; i < 5; i++) {
            new Thread(t).start();
        }
        // 写点什么代码，证明map的put不是线性安全的。
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(map.size());
    }
}

class T implements Runnable {
    Map map;

    public T(Map map) {
        this.map = map;
    }

    @Override
    public void run() {
        // 写点什么代码，证明map的put不是线性安全的。
        for (int i = 0; i < 1000; i++) {
            map.put(Thread.currentThread().getId() + "-" + i, i);
        }
    }
}
