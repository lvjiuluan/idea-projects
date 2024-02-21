package 韩顺平Java.Integer类;

public class Integer01 {
    public static void main(String[] args) {
        Integer integer = new Integer(1);
        Integer integer1 = Integer.valueOf(1);
        Integer integer2 = 1; // 等价于 Integer integer2 = Integer.valueOf(1);
        System.out.println(integer == integer1);
        System.out.println(integer1 == integer2);
        // 手动拆箱
        int i = integer1.intValue();
        // 自动拆箱
        int i2 = integer1;
        String.valueOf(new Integer01());
        Integer.parseInt("123");
    }
}
