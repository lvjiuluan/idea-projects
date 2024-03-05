package 韩顺平Java.集合02.map_;

import java.util.HashMap;
import java.util.Map;

public class Map04 {
    public static void main(String[] arg ) {
        Map map = new HashMap();
        map.put(new A(),"a"); //第一次会扩容
        for (int i = 0; i < 10; i++) {
            map.put(new A(),"a");
        }
        System.out.println(map);
    }
}

class A {
    @Override
    public int hashCode() {
        return 1;
    }
}