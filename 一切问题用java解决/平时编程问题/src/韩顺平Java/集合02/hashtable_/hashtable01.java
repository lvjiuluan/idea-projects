package 韩顺平Java.集合02.hashtable_;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class hashtable01 {
    public static void main(String[] args) {
        Map map = new Hashtable();
        for (int i = 0; i < 8; i++) {
            map.put(i,i);
        }
        map.put(8,8);
        System.out.println(map);

    }
}
