package 韩顺平Java.接口;

public interface Interface01 extends InterfaceFather1,InterfaceFather2 {
    int i = 0;  // 可以有静态变量，但必须初始化
    Integer i1 = null;
    default void read(){
        System.out.println("aaaa");
    }
    void pr();
    static void open(){

    }
}
