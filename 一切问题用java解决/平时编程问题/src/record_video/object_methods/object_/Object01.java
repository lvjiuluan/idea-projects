package record_video.object_methods.object_;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Object01 {
    public static void main(String[] args) {
        Person p1 = new Person("张三");
        Person p2 = new Person("张三");

        System.out.println(p1.equals(p2));

        Set set = new HashSet();

        set.add(p1);
        set.add(p2);

        System.out.println(set);

    }
}

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
