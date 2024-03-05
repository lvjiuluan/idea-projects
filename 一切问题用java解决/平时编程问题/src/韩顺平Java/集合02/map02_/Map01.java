package 韩顺平Java.集合02.map02_;

import java.util.HashMap;
import java.util.Map;

public class Map01 {
    public static void main(String[] args) {
        Map map = new HashMap();
        for (int i = 0; i < 12; i++) {
            map.put(new A(), i);
        }
    }
}

class A {
    @Override
    public int hashCode() {
        return 1;
    }
}
