package 韩顺平Java.类加载顺序;

public class Father {
    static Integer father_integer1;
    static int father_int1;

    static Integer father_integer2 = Integer.valueOf(1);
    static int father_int2 = 1;


    static {
        father_int1 = initInteger();
        printTp();
    }

    static int tp = 100;

    public static void printTp(){
        System.out.println("tp = " + tp);
    }

    public static int initInteger() {
        return 1;
    }

    Integer father_integer3;
    int father_int3;

    Integer father_integer4 = 1;
    int father_int4 = initInteger2();

    {
        father_int3 = initInteger2(); // 类加载完成_实例化阶段_初始化
    }

    public int initInteger2() {
        return 1;
    }

    public Father() {
        System.out.println("Father构造函数" + this);
    }


}
