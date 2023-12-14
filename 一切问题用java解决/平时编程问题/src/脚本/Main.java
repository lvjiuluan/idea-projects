package 脚本;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        SegmentTree segmentTree = new SegmentTree(arr);
        for (int i = 0; i < m; i++) {
            int instruct = scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            if (instruct == 1) {
                int k = scanner.nextInt();
                segmentTree.instruct1(x - 1, y - 1, k);
            } else {
                segmentTree.instruct2(x - 1, y - 1);
            }
        }
    }
}

class SegmentTree {
    int[] data;
    int[] lazy;
    int[] arr;

    SegmentTree(int[] arr) {
        this.arr = arr;
        data = new int[arr.length * 4];
        lazy = new int[arr.length * 4];
        createTree(arr, 1, 0, arr.length - 1);
    }

    private void createTree(int[] arr, int p, int l, int r) {
        if (l == r) {
            data[p] = arr[l];
            return;
        }
        int mid = (l + r) >> 1;
        createTree(arr, p << 1, l, mid);
        createTree(arr, p << 1 | 1, mid + 1, r);
        pushUp(p);
    }

    public void instruct1(int x, int y, int k) {
        update(1, x, y, 0, arr.length - 1, k);
    }

    public void instruct2(int x, int y) {
        System.out.println(query(1, x, y, 0, arr.length - 1));
    }

    private void pushUp(int p) {
        data[p] = data[p << 1] + data[p << 1 | 1];
    }

    private int query(int p, int ll, int rr, int l, int r) {
        if (r < ll || l > rr) return 0;
        if (l >= ll && r <= rr) {
            return data[p];
        }
        pushDown(p, l, r);
        int mid = (l + r) >> 1;
        return query(p << 1, ll, rr, l, mid) + query(p << 1 | 1, ll, rr, mid + 1, r);
    }

    private void update(int p, int ll, int rr, int l, int r, int k) {
        if (r < ll || l > rr) return;
        if (l >= ll && r <= rr) {
            data[p] += k * (r - l + 1);
            if (l < r) lazy[p] += k;
            return;
        }
        pushDown(p, l, r);
        int mid = (l + r) >> 1;
        update(p << 1, ll, rr, l, mid, k);
        update(p << 1 | 1, ll, rr, mid + 1, r, k);
        pushUp(p);
    }

    private void pushDown(int p, int l, int r) {
        if (lazy[p] != 0) {
            lazy[p << 1] += lazy[p];
            lazy[p << 1 | 1] += lazy[p];
            int mid = (l + r) >> 1;
            data[p << 1] += lazy[p] * (mid - l + 1);
            data[p << 1 | 1] += lazy[p] * (r - mid);
            lazy[p] = 0;
        }
    }


}
