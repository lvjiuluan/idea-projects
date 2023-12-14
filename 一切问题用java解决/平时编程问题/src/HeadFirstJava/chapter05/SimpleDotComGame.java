package HeadFirstJava.chapter05;

import java.util.Random;

public class SimpleDotComGame {
    public static void main(String[] args) {
        int numOfGuesses = 0;
        SimpleDotCom simpleDotCom = new SimpleDotCom();
        GameHelper helper = new GameHelper();
        Random random = new Random();
        int start = random.nextInt(5);
        int[] locations = {start, start + 1, start + 2};
        simpleDotCom.setLocationCells(locations);
        boolean isAlive = true;
        while (isAlive) {
            String userGuess = helper.getUserInput("enter a number");
            String result = simpleDotCom.checkYourself(userGuess);
            numOfGuesses++;
            if ("kill".equals(result)) {
                isAlive = false;
                System.out.println("You toke " + numOfGuesses + " guesses");
            }
        }
    }
}
