package 韩顺平Java.异常;

public class A {


    public static void main(String[] args) {
        /*String a = null;
        a.length(); // 空指针异常*/
       /* int[] arr = new int[2];
        System.out.println(arr[2]); // 数组越界异常*/
        /*System.out.println(1/0); // 算数异常
        String b = "ab";
        Integer.parseInt(b); //NumberFormatException*/
        /*Father father = new Father();
        System.out.println((Son) father);*/
//        System.out.println((A) father);
        Object obj = new Father();

//        System.out.println((A) obj);
        System.out.println((Son) obj);
    }
}

