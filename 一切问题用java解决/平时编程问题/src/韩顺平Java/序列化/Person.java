package 韩顺平Java.序列化;

import java.io.Serializable;

public class Person implements Serializable {
    String name;
    String sex;

    public Person(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
