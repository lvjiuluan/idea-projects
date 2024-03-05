package 韩顺平Java.集合02.map_;

import java.util.HashMap;
import java.util.Map;

public class map05 {
    public static void main(String[] args) {
        Map map = new HashMap(12);
        for (int i = 0; i < 12; i++) {
            map.put(i, i);
        }
        map.put(12, 12);
        System.out.println(map);
    }
}
/*
* oldTab @504
* table @544
* */