package 悟空并发编程.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IDCollector {

    public static List<String> collectIDs() {
        List<String> idList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("请输入ID（按回车键结束输入）：");

        // 不断接收用户输入，直到输入空行
        while (true) {
            input = scanner.nextLine();
            if (input.isEmpty()) {
                break; // 如果输入为空行，退出循环
            }
            idList.add(input); // 将输入的ID添加到列表中
        }

        return idList; // 返回ID列表
    }

    public static void main(String[] args) {
        List<String> ids1 = collectIDs();
        System.out.println("您输入的ID列表大小是：" + ids1.size());

        List<String> ids2 = collectIDs();
        System.out.println("您输入的ID列表大小是：" + ids2.size());

        System.out.println("两个列表的交集是："+getIntersection(ids1,ids2));
    }

    /**
     * 返回两个列表的交集
     * @param list1 第一个列表
     * @param list2 第二个列表
     * @return 两个列表的交集
     */
    public static List<String> getIntersection(List<String> list1, List<String> list2) {
        // 创建一个新的列表来存储交集
        List<String> intersection = new ArrayList<>(list1);

        // 使用 retainAll 方法保留两个列表中的共同元素
        intersection.retainAll(list2);

        return intersection;
    }
}
