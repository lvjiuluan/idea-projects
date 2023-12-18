package HeadFirstJava.chapter02.guessGame;

public class GuessGame {
    Player[] players = new Player[3];

    void startGame() {
        for (int i = 0; i < 3; i++) {
            players[i] = new Player();
        }
        int target = (int) (Math.random() * 10);
        System.out.println("The target is " + target);
        int k = 1;
        while (true) {
            System.out.println("In the " + k + " iteration");
            for (int i = 0; i < 3; i++) {
                players[i].guess();
                int guess = players[i].number;
                System.out.println("p" + (i + 1) + " guesses " + guess);
                if (guess == target) {
                    System.out.println("p" + (i + 1) + " win the game");
                    return;
                }
            }
            k += 1;
        }

    }
}
