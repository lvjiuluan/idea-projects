package 韩顺平Java.Integer类;

public class Integer02 {
    public static void main(String[] args) {
        Integer i =  new Integer(1);
        Integer j = new Integer(1);

        System.out.println("false = " + (i == j));

        Integer k = 1;
        Integer p = 1;
        Integer q = Integer.valueOf(1);

        System.out.println("true = " + (k == p));

        Integer m = 128;
        Integer n = 128;

        System.out.println("false = " + (m == n));

        int o = 128;
        System.out.println(n == o);
    }
}
