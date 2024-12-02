package 悟空并发编程.字典树;

import java.util.Scanner;

public class TrieTest {
    public static void main(String[] args) {
        Trie trie = new Trie();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            trie.insert(scanner.nextLine());
        }
        int m = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < m; i++) {
            int query = trie.query(scanner.nextLine());
            switch (query){
                case 0:
                    System.out.println("WRONG");
                    break;

                case 1:
                    System.out.println("OK");
                    break;
                default:
                    System.out.println("REPEAT");
                    break;
            }
        }
    }
}
