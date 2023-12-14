package 脚本;

public class SegmentTree {
    int[] data;
    int[] lazy;

    SegmentTree(int[] arr) {
        // arr 从 1 开始编号
        data = new int[4 * arr.length];
        lazy = new int[4 * arr.length];
        creatTree(arr, 1, 1, arr.length - 1);
    }

    private void creatTree(int[] arr, int p, int l, int r) {
        if (l == r) {
            data[p] = arr[l];
            return;
        }
        // 这里必须把mid记录下来，不能用(l + r) >> 1
        int mid = (l + r) / 2;
        creatTree(arr, p << 1, l, mid);
        creatTree(arr, p << 1 | 1, mid + 1, r);
        pushUp(p);
    }

    private void pushUp(int p) {
        data[p] = data[p << 1] + data[p << 1 | 1];
    }

    private void pushDown(int p, int l, int r) {
        if (lazy[p] != 0) {
            // 下移lazy标记
            lazy[p << 1] += lazy[p];
            lazy[p << 1 | 1] += lazy[p];
            int mid = (l + r) >> 1;
            data[p << 1] += lazy[p] * (mid - l + 1);
            data[p << 1 | 1] += lazy[p] * (r - mid);
            // 清除lazy标记
            lazy[p] = 0;
        }
    }

    public void updateOne(int p, int i, int l, int r, int k) {
        if (l == r && i == l) {
            data[p] += k;
            return;
        }
        if (l < r) {
            int mid = (l + r) / 2;
            updateOne(p << 1, i, l, mid, k);
            updateOne(p << 1 | 1, i, mid + 1, r, k);
            pushUp(p);
        }
    }

    public void update(int p, int ll, int rr, int l, int r, int k) {
        if (r < ll || l > rr) return;
        if (l >= ll && r <= rr) {
            data[p] += k * (r - l + 1);
            lazy[p] += k;
            return;
        }
        pushDown(p, l, r);
        int mid = (l + r) >> 1;
        update(p << 1, ll, rr, l, mid, k);
        update(p << 1 | 1, ll, rr, mid + 1, r, k);
        pushUp(p);
    }

    public int query(int p, int ll, int rr, int l, int r) {
        if (r < ll || l > rr) return 0;
        if (l >= ll && r <= rr) {
            return data[p];
        }
        pushDown(p, l, r);
        int mid = (l + r) >> 1;
        return query(p << 1, ll, rr, l, mid) + query(p << 1 | 1, ll, rr, mid + 1, r);
    }

    public static void main(String[] args) {
        int[] arr = {0, 2, 3, 1, 4};
        SegmentTree segmentTree = new SegmentTree(arr);
        segmentTree.update(1,1,3,1,arr.length-1,1);
        System.out.println(segmentTree.query(1,1,3,1,arr.length-1));
        System.out.println(segmentTree.query(1,1,1,1,arr.length-1));
        System.out.println(segmentTree.query(1,1,2,1,arr.length-1));
    }
}
