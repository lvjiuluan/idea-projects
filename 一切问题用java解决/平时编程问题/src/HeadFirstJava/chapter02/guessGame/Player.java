package HeadFirstJava.chapter02.guessGame;

public class Player {
    int number;

    void guess() {
        this.number = (int) (Math.random() * 10);
    }
}
