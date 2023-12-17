package 每周20道力扣.第五周;


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Queue;

public class Solution621 {


    public int leastInterval(char[] tasks, int n) {
        int[] map = new int[26];
        for (char task : tasks) {
            map[task - 'A']++;
        }
        Arrays.sort(map);
        int res = map[map.length - 1];
        for (int i = map.length - 2; i >= 0; i--) {
            if (map[i] == map[map.length - 1]) res++;
        }
        return res > tasks.length ? res : tasks.length;
    }

    public static void main(String[] args) {
        int a = 'Z' - 'A';
        System.out.println((char) (26 + 'A'));
        int[] nums = {1, 2, 3};

    }
}
