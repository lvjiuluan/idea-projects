package 韩顺平Java.序列化;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void writeObj(Object obj) throws IOException{
        URL url = Main.class.getResource("");
        String path = URLDecoder.decode(url.getPath(), "utf-8");
        path = path + "Person.ser";
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
        System.out.println(path);
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(obj);

        objectOutputStream.close();
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, URISyntaxException {
        /*Person person = new Person("张三", "男");
        writeObj(person);*/
        /*Object obj = readObj("Person.ser");
        System.out.println(obj);*/
       /* URL url = Main.class.getResource("");
        URI uri = url.toURI();
        Path path = Paths.get(uri);
        System.out.println(path.resolve("Persion.ser"));*/
    }

    private static Object readObj(String fileName) throws IOException, ClassNotFoundException {
        URL url = Main.class.getResource("");
        String path = URLDecoder.decode(url.getPath(), "utf-8");
        path = path + fileName;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object obj = objectInputStream.readObject();
        return obj;
    }
}
