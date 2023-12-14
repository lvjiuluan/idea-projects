package HeadFirstJava.chapter05;

import java.util.Scanner;

public class GameHelper {
    public String getUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt + " ");
        String inputLine = scanner.next();
        return inputLine;
    }

}
