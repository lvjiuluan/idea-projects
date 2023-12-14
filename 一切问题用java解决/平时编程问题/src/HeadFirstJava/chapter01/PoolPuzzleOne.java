package HeadFirstJava.chapter01;

public class PoolPuzzleOne {
    public static void main(String[] args) {
        int x = 0;
        while (x < 3) {
            System.out.print("a");
            if (x < 1) {
                System.out.print(" ");
            }
            System.out.print("n");
            if (x < 1) {
                System.out.print("oise");
            }
            if (x == 1) {
                System.out.print("noys");
            }
            if (x > 1) {
                System.out.print(" oyster");
            }
            System.out.println("");
            x += 1;
        }
    }
}
