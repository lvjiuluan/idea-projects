package 悟空并发编程.字典树;

public class IPlusTest {
    static int i = 0;
    public static int getI() {
        return i++;
    }

    public static void main(String[] args) {
        System.out.println(getI());
        System.out.println(i);
    }
}
