package 悟空并发编程.字典树.二叉前缀树;

import java.util.Scanner;

public class KthLexStringFinder {
    private static int k; // 目标序号
    private static String result = null; // 存储结果
    private static int found = 0; // 计数器

    public static void main(String[] args) {
       /* Scanner scanner = new Scanner(System.in);

        // 输入最大长度 n 和目标序号 k
        System.out.print("请输入最大长度 n: ");
        int n = scanner.nextInt();
        System.out.print("请输入目标序号 k: ");
        k = scanner.nextInt();
        scanner.close();
        */
        int n = 3;
        for (int i = 1; i < 14; i++) {
            k = i;
            found = 0;
            result = null;
            // 开始回溯搜索
            backtrack(new StringBuilder(), n);

            if (result != null) {
                System.out.println("第 " + k + " 个字符串是: " + result);
            } else {
                System.out.println("不存在第 " + k + " 个字符串。");
            }
        }
    }

    /**
     * 回溯函数，用于生成所有可能的字符串并找到第 k 个
     * @param current 当前构建的字符串
     * @param maxLen 最大长度
     */
    private static void backtrack(StringBuilder current, int maxLen) {
        // 如果当前字符串长度大于0，考虑将其计入结果
        if (current.length() > 0) {
            found++;
            if (found == k) {
                result = current.toString();
                return;
            }
        }

        // 如果达到最大长度，返回
        if (current.length() == maxLen) {
            return;
        }

        // 尝试添加字符 'a'
        current.append('a');
        backtrack(current, maxLen);
        if (result != null) return; // 如果找到结果，停止搜索
        current.deleteCharAt(current.length() - 1); // 回溯

        // 尝试添加字符 'b'
        current.append('b');
        backtrack(current, maxLen);
        if (result != null) return; // 如果找到结果，停止搜索
        current.deleteCharAt(current.length() - 1); // 回溯
    }
}

