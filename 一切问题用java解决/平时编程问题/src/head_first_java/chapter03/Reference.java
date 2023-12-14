package head_first_java.chapter03;

public class Reference {
    public static void main(String[] args) {
        int[] arr = new int[3];
        System.out.println(arr[0]);
        Integer i = Integer.valueOf(1);
        Integer i2 = null;
        System.out.println(i2);
        i += 1;
        System.out.println(i.intValue());
    }
}
