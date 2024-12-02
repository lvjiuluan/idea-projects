package 每周20道力扣.第七周;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class DayOfYear {
    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);
        String date = scanner.nextLine();
        System.out.println(getDayOfYear(date));

    }

    private static int getDayOfYear(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return localDate.getDayOfYear();
    }
}
