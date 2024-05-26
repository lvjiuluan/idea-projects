package 每周20道力扣.第四周;

public class 最长回文子串 {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("aacabdkacaa"));
        ;
    }

    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 1) return s;
        int n = s.length();
        String[][] dp = new String[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = String.valueOf(s.charAt(i));
        }
        String res = "";
        for (int R = 1; R < n; R++) {
            for (int L = 0; L < R; L++) {
                if (s.charAt(L) == s.charAt(R)) {
                    if (L == R - 1) {
                        dp[L][R] = String.valueOf(s.charAt(L)) + String.valueOf(s.charAt(R));
                        if (dp[L][R].length() > res.length()) {
                            res = dp[L][R];
                        }
                    } else if (dp[L + 1][R - 1] != null) {
                        dp[L][R] = s.charAt(L) + dp[L + 1][R - 1] + s.charAt(R);
                        if (dp[L][R].length() > res.length()) {
                            res = dp[L][R];
                        }
                    }

                }
            }
        }
        return res;
    }
}

