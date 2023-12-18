package 脚本;

import java.io.*;

public class 从references导入标题 {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("平时编程问题\\resources\\references.bib"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("平时编程问题\\resources\\titles.txt"));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.trim().startsWith("title")) {
                int start = line.indexOf("{") + 1;
                int end = line.indexOf("}");
                String title = line.substring(start, end);
                bufferedWriter.write(title);
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.close();
        bufferedReader.close();
    }
}
