package 韩顺平Java.JVM优化;

public class Demo01 {
    public static void main(String[] args) {
        byte[] array1 = new byte[1024 * 1024];  // 1M 数组
        array1 = new byte[1024 * 1024]; // 1M 数组
        array1 = new byte[1024 * 1024]; // 1M 数组
        array1 = null;

        Object o = new Object();
        o.toString();
        String s = "";
        Integer i = new Integer(1);
        i.hashCode();
//        s.equals();
        byte[] array2 = new byte[2 * 1024 * 1024]; // 2M数组
    }
}
