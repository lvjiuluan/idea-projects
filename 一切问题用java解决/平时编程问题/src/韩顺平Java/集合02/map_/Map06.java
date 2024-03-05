package 韩顺平Java.集合02.map_;

import java.util.HashMap;
import java.util.Map;

public class Map06 {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("java", 10);
        map.put("php", 10);
        map.put("java", 20);

        System.out.println("map = " + map);
    }
}
