package 每周20道力扣.第六周;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution51 {
    public int n;
    public int[][] borad = new int[10][10];
    public int count;
    public List<List<String>> lists = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        this.n = n;
        dfs(0, 0, 0);
        return lists;
    }

    public String getString(int[] row) {
        char[] rowChar = new char[n];
        Arrays.fill(rowChar, '.');
        for (int i = 0; i < n; i++) {
            if (row[i] == 1) rowChar[i] = 'Q';
        }
        return String.valueOf(rowChar);
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(getString(borad[i]));
        }

        return list;
    }

    public boolean legal(int i, int j) {
        for (int x = 0; x < n; x++)
            for (int y = 0; y < n; y++) {
                if (borad[x][y] == 1) {
                    int dx = Math.abs(x - i);
                    int dy = Math.abs(y - j);
                    if (dx == 0 || dy == 0 || dx == dy) return false;
                }
            }
        return true;
    }

    public void dfs(int i, int j, int k) {
        if (k == n) {
            lists.add(getList());
            return;
        }
        for (int x = i; x < n; x++)
            for (int y = 0; y < n; y++) {
                if (!legal(x, y)) continue;
                borad[x][y] = 1;
                dfs(x, y, k + 1);
                borad[x][y] = 0;
            }
    }

    public void printBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(borad[i][j] + " ");
            }
            System.out.println();
        }
    }

//    public static void main(String[] args) {
//        Solution51 solution51 = new Solution51();
//        List<List<String>> lists = solution51.solveNQueens(4);
//        System.out.println(lists);
//    }
}

