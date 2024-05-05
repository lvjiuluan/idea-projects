package 韩顺平Java.线程安全的Map.辅助操作;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedMap {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        // 返回Map接口实现类对象，synchronizedMap
        /*
        * private static class SynchronizedMap<K,V>
          implements Map<K,V>, Serializable
        * */
        Map<String, Integer> synchronizedMap = Collections.synchronizedMap(map);
        System.out.println(synchronizedMap);
    }
}
