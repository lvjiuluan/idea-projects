package 韩顺平Java.集合02.map03_;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynMap01 {
    public static void main(String[] args) {
        Map map = new HashMap();
        Map synMap = Collections.synchronizedMap(map);
        synMap.put("a", "a");
    }
}
