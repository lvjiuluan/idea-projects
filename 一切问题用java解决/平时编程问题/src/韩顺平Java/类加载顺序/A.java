package 韩顺平Java.类加载顺序;

public class A {
    String s1 = "abc";
    String s2 = new String("abc");
    Integer i1 = 12;
    Integer i2 = new Integer(12);

    public static void dfs(int i){
        System.out.println(i++);
        dfs(i);
    }

    public static void main(String[] args) throws InterruptedException {
//        dfs(0);
        // 10K can not create a Java Virtual Machine
        // 108K 973
        // 1M 11407
        // 10M 98582
      /*  Thread.sleep(1000 * 5);
        int[] arr;
        while (true){
            arr = new int[10000]; // 4B * 10000 = 40KB
            Thread.sleep(1000 * 5);
        }*/

        Integer i1 = 128;
        Integer i2 = new Integer(128);

        System.out.println(i1 == i2); // T

        Integer i3 = 12;
        Integer i4 = new Integer(12);

        System.out.println(i3 == i4); // T

        Integer i5 = 12;
        Integer i6 = Integer.valueOf(12);
        System.out.println(i5 == i6); // T

        Integer i7 = Integer.valueOf(128);
        Integer i8 = Integer.valueOf(128);
        System.out.println(i7 == i8); // F

        i7 = Integer.valueOf(12);
        i8 = Integer.valueOf(12);
        System.out.println(i7 == i8); // T

        // byte类型的表示范围
        // [-128,127]
        // [-127,127]
        // 最大：0 1111111
        // 最小：1 1111111
        // +0： 0 0000000
        // -0： 1 0000000  -128
    }
}
