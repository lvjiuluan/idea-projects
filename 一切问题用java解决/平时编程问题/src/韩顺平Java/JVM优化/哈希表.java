package 韩顺平Java.JVM优化;

import java.util.HashMap;
import java.util.Map;

public class 哈希表 {
    public static void main(String[] args) {
        Map<My, String> map = new HashMap<>();
        My my = new My(1);
        map.put(my,"a");
        map.put(my,"a");
        System.out.println(map);
    }
}

class My {
    Integer i;

    public My(Integer i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return i.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return i.equals(obj);
    }
}
