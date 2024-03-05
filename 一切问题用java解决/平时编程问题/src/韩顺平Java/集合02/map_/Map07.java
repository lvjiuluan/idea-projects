package 韩顺平Java.集合02.map_;

import java.util.HashMap;
import java.util.Map;

public class Map07 {
    public static void main(String[] args) {
        Map map = new HashMap();
        for (int i = 0; i < 13; i++) {
            map.put(i, i);
        }
        System.out.println(map);
        for (int i = 0; i < 13; i++) {
            map.remove(i);
        }
        System.out.println(map);
    }
}
