package 韩顺平Java.类加载顺序;

public class Son extends Father {
    static Integer son_integer1;
    static int son_int1;

    static Integer son_integer2 = Integer.valueOf(1);
    static int son_int2 = 1;


    static {
        son_int1 = initInteger();
    }



    public static int initInteger() {
        return 1;
    }

    Integer son_integer3;
    int son_int3;

    Integer son_integer4 = 1;
    int son_int4 = initInteger2();

    {
        son_int3 = initInteger2();
    }

    public int initInteger2() {
        return 1;
    }

    public Son() {
        System.out.println("Son构造函数" + this);
    }


}
