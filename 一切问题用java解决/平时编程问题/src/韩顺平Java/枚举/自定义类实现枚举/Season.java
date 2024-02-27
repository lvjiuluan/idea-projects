package 韩顺平Java.枚举.自定义类实现枚举;

public class Season {

    public static final Season SPRING = new Season("春天");
    public static final Season SUMMER = new Season("夏天");
    public static final Season AUTUMN = new Season("秋天");
    public static final Season WINTER = new Season("冬天");

    String name;

    private Season(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
    }
}
