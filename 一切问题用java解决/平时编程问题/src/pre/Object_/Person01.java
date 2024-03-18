package pre.Object_;

import java.util.*;

public class Person01 {
    public static void main(String[] args) {
        Person p1 = new Person("张三");
        Person p2 = new Person("张三");

        Set set = new HashSet();

        System.out.println(p1.equals(p2));

        set.add(p1);
        set.add(p2);

        System.out.println(set.size());
    }
}

class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Person person = (Person) o;
//        return name.equals(person.name);
//    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
