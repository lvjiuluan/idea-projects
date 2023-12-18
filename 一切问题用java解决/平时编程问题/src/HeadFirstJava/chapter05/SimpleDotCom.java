package HeadFirstJava.chapter05;

public class SimpleDotCom {
    private int[] locations;
    private int numOfHits;

    public void setLocationCells(int[] locations) {
        this.locations = locations;
    }

    public String checkYourself(String userGuess) {
        int guess = Integer.valueOf(userGuess);
        String result = "miss";
        for (int location : locations) {
            if (guess == location) {
                result = "hit";
                numOfHits++;
                break;
            }
        }
        if (numOfHits == locations.length) {
            result = "kill";
        }
        System.out.println(result);
        return result;
    }
}
