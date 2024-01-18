package 脚本;

public class Learn09 {
    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) return s;
        int n = s.length();
        String[][] dp = new String[n][n];

        // 初始化 以l为左端点，以l为右端点的的字符串
        for (int i = 0; i < n; i++) {
            dp[i][i] = String.valueOf(s.charAt(i));
        }

        String maxStr = String.valueOf(s.charAt(0));

        // 开始遍历dp
        for (int R = 1; R < n; R++) {
            for (int L = 0; L < R; L++) {
                if (s.charAt(L) == s.charAt(R)) {
                    if (L + 1 > R - 1) {
                        dp[L][R] = s.substring(L, R + 1);
                        if (dp[L][R].length() > maxStr.length()) {
                            maxStr = dp[L][R];
                        }
                    } else if (dp[L + 1][R - 1] != null) {
                        dp[L][R] = s.charAt(L) + dp[L + 1][R - 1] + s.charAt(R);
                        if (dp[L][R].length() > maxStr.length()) {
                            maxStr = dp[L][R];
                        }
                    }
                }
            }
        }
        return maxStr;
    }

    public static void main(String[] args) {
        Learn09 learn09 = new Learn09();
        String str = learn09.longestPalindrome("badad");
        System.out.println(str);
    }
}
