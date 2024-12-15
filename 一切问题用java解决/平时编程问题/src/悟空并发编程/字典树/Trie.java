package 悟空并发编程.字典树;

public class Trie {
    public static final int MAXN = 100000;
    // 当前单词的下一个字母所在行数
    private int idx = 0;
    private int[][] tr = new int[MAXN][26];
    private int[] cnt = new int[MAXN];

    public void insert(String s){
        int p = 0;
        for (int i = 0; i < s.length(); i++) {
            int x = s.charAt(i) - 'a';
            if(tr[p][x] == 0){
                tr[p][x] = ++idx;
            }
            p = tr[p][x];
        }
        cnt[p]++;
    }

    public int query(String s){
        int p = 0;
        for (int i = 0; i < s.length(); i++) {
            int x = s.charAt(i) - 'a';
            if(tr[p][x] == 0){
                return 0;
            }
            p = tr[p][x];
        }
        return cnt[p]++;
    }

    public void printTR(){
        for (int i = 0; i < tr.length; i++) {
            for (int j = 0; j < tr[0].length; j++) {
                System.out.print(tr[i][j]+" ");
            }
            System.out.println();
        }
    }
}
