package 每周20道力扣.第五周;

public class 砍竹子 {
    public static long powMod(long x, long n, long mod) {
        long res = 1;
        while (n != 0) {
            if ((n & 1) == 1) {
                res %= mod;
                x %= mod;
                res *= x;
                res %= mod;
                x %= mod;
            }
            x %= mod;
            x *= x;
            x %= mod;
            n >>= 1;
        }
        return (res % mod);
    }

    public static int cuttingBamboo(int bamboo_len) {
        int mod = 1000000007;
        if (bamboo_len <= 3) return bamboo_len - 1;
        int a = bamboo_len / 3;
        int b = bamboo_len % 3;
        if (b == 0) return (int)(powMod(3, a, mod) % mod);
        if (b == 1) return (int)((powMod(3, a - 1, mod) * 4) % mod);
        return (int)(powMod(3, a, mod) * 2 % mod);
    }

    public static void main(String[] args) {
        System.out.println(cuttingBamboo(127));
    }
}
