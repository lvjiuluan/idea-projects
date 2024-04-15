package main.competition;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Graph {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        GraphNumber[] graphNumbers = new GraphNumber[n];
        for (int i = 0; i < n; i++) {
            int num = scanner.nextInt();
            graphNumbers[i] = new GraphNumber(num);
        }
        Arrays.sort(graphNumbers, new Comparator<GraphNumber>() {
            @Override
            public int compare(GraphNumber o1, GraphNumber o2) {
                if (o1.graphNum != o2.graphNum) {
                    return o1.graphNum - o2.graphNum;
                } else {
                    return o1.a - o2.a;
                }
            }
        });
        for (GraphNumber graphNumber : graphNumbers) {
            System.out.print(graphNumber + " ");
        }

    }
}

class GraphNumber {
    int a;
    int graphNum;
    static int[] map = {1, 0, 0, 0, 1, 0, 1, 0, 2, 1};

    public GraphNumber(int a) {
        this.a = a;
        graphNum = 0;
        String strNum = String.valueOf(a);
        for (int i = 0; i < strNum.length(); i++) {
            char c = strNum.charAt(i);
            graphNum += map[c - '0'];
        }
    }

    @Override
    public String toString() {
        return String.valueOf(a);
    }

}