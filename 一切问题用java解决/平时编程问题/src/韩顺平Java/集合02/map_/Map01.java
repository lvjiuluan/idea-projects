package 韩顺平Java.集合02.map_;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Map01 {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("no1", "韩顺平");
        map.put("no2", "张无忌");
        map.put("no3", "张无忌");
        map.put("abc", null);
        map.put("no4", null);

        Set set = map.entrySet();


        System.out.println(map);
    }
}
