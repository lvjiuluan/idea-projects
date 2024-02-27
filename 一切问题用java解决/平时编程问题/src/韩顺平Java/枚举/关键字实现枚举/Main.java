package 韩顺平Java.枚举.关键字实现枚举;

public class Main {
    public static void main(String[] args) {
        Season spring = Season.valueOf("SPRING");
        System.out.println(spring.compareTo(Season.AUTUMN));
    }
}
