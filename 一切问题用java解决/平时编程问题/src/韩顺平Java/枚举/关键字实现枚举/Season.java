package 韩顺平Java.枚举.关键字实现枚举;

import 韩顺平Java.接口.Interface01;

public enum Season  implements Interface01 {
    SPRING("春"),  // 等价于 public static final SPRING = new Season("春")
    SUMMER("夏"),
    AUTUMN("秋"),
    WINTER("东");

    private String name;

    Season(String name) {  // 默认用private修饰
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public void pr() {

    }
}
