package 韩顺平Java.重载;

public class Main {
    public static void main(String[] args) {
        Father father = new Son();
        father.speak(1); // ==> Father.speak()调用的
    }
}
