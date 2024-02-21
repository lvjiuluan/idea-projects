package 每周20道力扣.第六周.string_;

public class Gen<T> {
    T get(T a) {
        return a;
    }

    public static void main(String[] args) {
        Gen<Integer> integerGen = new Gen<>();
        Integer integer = integerGen.get(2);
        System.out.println(integer);
    }
}
