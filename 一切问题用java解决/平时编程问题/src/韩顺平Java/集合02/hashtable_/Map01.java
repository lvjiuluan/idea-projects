package 韩顺平Java.集合02.hashtable_;

import java.util.HashMap;
import java.util.Map;

public class Map01 {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put(1,null);
        map.put(2,null);
        System.out.println(map.get(1));
        System.out.println(map.get(2));
        System.out.println(map.get(3));
    }
}
