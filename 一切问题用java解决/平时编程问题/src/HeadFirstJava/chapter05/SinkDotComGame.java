package HeadFirstJava.chapter05;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SinkDotComGame {
    private String[][] borard;
    private boolean[][] visited;
    private String[] dot1;
    private String[] dot2;
    private String[] dot3;
    private Map<String, Cordinate> map;
    private Cordinate[] cordinates1;
    private Cordinate[] cordinates2;
    private Cordinate[] cordinates3;
    private Map<Cordinate[], String> cordinates2String;

    public class Cordinate {
        int i;
        int j;

        public Cordinate(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public Cordinate next(int[] d) {
            Cordinate cordinate = new Cordinate(this.i + d[0], this.j + d[1]);
            return cordinate;
        }
    }

    public SinkDotComGame() {
        this.borard = new String[7][7];
        this.visited = new boolean[7][7];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++) {
                this.borard[i][j] = "";
                this.visited[i][j] = false;
            }
        this.dot1 = new String[]{"Go2", ".", "com"};
        this.dot2 = new String[]{"Pets", ".", "com"};
        this.dot3 = new String[]{"AskMe", ".", "com"};
        map = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            char c = (char) ('A' + i);
            String s = String.valueOf(c);
            for (int j = 0; j < 7; j++) {
                map.put(s + j, new Cordinate(i, j));
            }
        }
    }

    boolean isLegal(Cordinate cordinate) {
        int i = cordinate.i;
        int j = cordinate.j;
        return i < 7 && i >= 0 && j < 7 && j >= 0 && this.borard[i][j].equals("");
    }

    Cordinate[] generateThreeCordinates() {
        Cordinate cordinate1 = new Cordinate(-1, -1);
        Cordinate cordinate2 = new Cordinate(-1, -1);
        Cordinate cordinate3 = new Cordinate(-1, -1);
        while (!isLegal(cordinate1)) {
            cordinate1.i = (int) (Math.random() * 7);
            cordinate1.j = (int) (Math.random() * 7);
        }
        int[][] steps = {{-1, 0}, {+1, 0}, {0, -1}, {0, +1}};
        while (!(isLegal(cordinate1) && isLegal(cordinate2) && isLegal(cordinate3))) {
            int direction = (int) (Math.random() * 4);
            cordinate2 = cordinate1.next(steps[direction]);
            cordinate3 = cordinate2.next(steps[direction]);
        }
        Cordinate[] cordinates = {cordinate1, cordinate2, cordinate3};
        return cordinates;
    }

    public void printBoard() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (this.borard[i][j].equals("")) {
                    System.out.print(String.format("%7s", "*"));
                } else if (this.borard[i][j].equals(".")) {
                    System.out.print(String.format("%7s", "."));
                } else {
                    System.out.print(String.format("%7s", this.borard[i][j]));
                }
            }
            System.out.println();
        }
    }

    public void initBoard(String[] dot, Cordinate[] cordinates) {
        for (int i = 0; i < 3; i++) {
            int x = cordinates[i].i;
            int y = cordinates[i].j;
            this.borard[x][y] = dot[i];
        }
    }

    public void initBoard() {
        cordinates2String = new HashMap<>();
        this.cordinates1 = generateThreeCordinates();
        cordinates2String.put(this.cordinates1, String.join("", this.dot1));
        initBoard(this.dot1, this.cordinates1);
        this.cordinates2 = generateThreeCordinates();
        cordinates2String.put(this.cordinates2, String.join("", this.dot2));
        initBoard(this.dot2, this.cordinates2);
        this.cordinates3 = generateThreeCordinates();
        cordinates2String.put(this.cordinates3, String.join("", this.dot3));
        initBoard(this.dot3, this.cordinates3);
    }

    public boolean judgeKill(Cordinate[] cordinates) {
        boolean flag = true;
        for (Cordinate cordinate : cordinates) {
            flag = flag && this.visited[cordinate.i][cordinate.j];
        }
        return flag;
    }

    public Cordinate[] judgeKill() {
        if (judgeKill(this.cordinates1)) {
            return this.cordinates1;
        }
        if (judgeKill(this.cordinates2)) {
            return this.cordinates2;
        }
        if (judgeKill(this.cordinates3)) {
            return this.cordinates3;
        }
        return null;
    }

    public void clear(Cordinate[] cordinates) {
        for (Cordinate cordinate : cordinates) {
            this.borard[cordinate.i][cordinate.j] = "";
        }

        this.cordinates2String.remove(cordinates);

    }

    public void guessOnce(String guess) {
        if (judgeKill(this.cordinates1) && judgeKill(this.cordinates2) && judgeKill(this.cordinates3)) {
            System.out.println("You have sink all ships!");
            return;
        }
        System.out.println("Enter a guess " + guess);
        Cordinate cordinate = map.get(guess);
        if (cordinate != null) {
            int i = cordinate.i;
            int j = cordinate.j;
            if (this.borard[i][j].equals(".") ||
                    this.borard[i][j].equals("com") ||
                    this.borard[i][j].equals("Go2") ||
                    this.borard[i][j].equals("Pets") ||
                    this.borard[i][j].equals("AskMe")) {
                this.visited[i][j] = true;
                Cordinate[] cordinates = judgeKill();
                if (cordinates != null) {
                    System.out.println("Ouch! You sunk " + cordinates2String.get(cordinates));
                    clear(cordinates);
                } else {
                    System.out.println("hit!");
                }
            } else {
                System.out.println("miss");
            }
        } else {
            System.out.println("输入的位置有错！");
        }
    }

    public static void main(String[] args) {
        SinkDotComGame sinkDotComGame = new SinkDotComGame();
        Cordinate[] cordinates = sinkDotComGame.generateThreeCordinates();
        for (Cordinate cordinate : cordinates) {
            System.out.println(cordinate.i + "," + cordinate.j + "：" + sinkDotComGame.isLegal(cordinate));
        }
        sinkDotComGame.initBoard();
        sinkDotComGame.printBoard();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            sinkDotComGame.guessOnce(line);
        }
    }
}
