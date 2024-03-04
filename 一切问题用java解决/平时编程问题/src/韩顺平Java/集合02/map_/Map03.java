package 韩顺平Java.集合02.map_;

import java.util.*;

public class Map03 {
    public void iterateMap01(Map map) {
        for (Object o : map.keySet()) {
            System.out.print(o + "=" + map.get(o) + "  ");
        }
        System.out.println("\n=============================");
    }

    public void iterateMap02(Map map) {
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            System.out.print(entry.getKey() + "=" + entry.getValue() + "  ");
        }
        System.out.println("\n=============================");
    }

    public void iterateMap03(Map map) {
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            System.out.print(o + "=" + map.get(o) + "  ");
        }
        System.out.println("\n=============================");
    }

    public void iterateMap04(Map map) {
        for (Object value : map.values()) {
            System.out.print(value + "  ");
        }

        System.out.println("\n=============================");
    }

    public void iterateMap05(Map map) {
        Collection values = map.values();
        Iterator iterator = values.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next());
        }
        System.out.println("\n=============================");
    }

    public void iterateMap06(Map map) {
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            Map.Entry entry = (Map.Entry) o;
            System.out.print(entry.getKey() + "=" + entry.getValue() + "  ");
        }
        System.out.println("\n=============================");
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("no1", "韩顺平");
        map.put("no2", "张无忌");
        map.put("no3", "张无忌");
        map.put("abc", null);
        map.put("no4", null);

        Map03 map03 = new Map03();
        map03.iterateMap01(map);
        map03.iterateMap02(map);
        map03.iterateMap03(map);
        map03.iterateMap04(map);
        map03.iterateMap05(map);
        map03.iterateMap06(map);
    }
}
