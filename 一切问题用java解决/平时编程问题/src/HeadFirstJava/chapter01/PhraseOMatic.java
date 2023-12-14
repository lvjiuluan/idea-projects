package HeadFirstJava.chapter01;

public class PhraseOMatic {
    public static void main(String[] args) {
        String[] var1 = new String[]{"24/7", "multi-Tier", "30,000 foot", "B-to-B", "win-win", "front-end", "web-based", "pervasive", "smart", "six-sigma", "critical-path", "dynamic"};
        String[] var2 = new String[]{"empowered", "sticky", "value-added", "oriented", "centric", "distributed", "clustered", "branded", "outside-the-box", "positioned", "networked", "focused", "leveraged", "aligned", "targeted", "shared", "cooperative", "accelerated"};
        String[] var3 = new String[]{"process", "tipping-point", "solution", "architecture", "core competency", "strategy", "mindshare", "portal", "space", "vision", "paradigm", "mission"};
        // find out how many words are in each list
        int numWrodsofVar1 = var1.length;
        int numWrodsofVar2 = var2.length;
        int numWrodsofVar3 = var3.length;
        // generate three random numbers
        int rand1 = (int) (Math.random() * numWrodsofVar1);
        int rand2 = (int) (Math.random() * numWrodsofVar2);
        int rand3 = (int) (Math.random() * numWrodsofVar3);
        // now build a phrase
        String phrase = var1[rand1] + " " + var2[rand2] + " " + var3[rand3];
        // print out the phrase
        System.out.println(phrase);
        Object i = Integer.valueOf(101);
//        String s = (String) i;
        Object[] objects = new Object[2];
        objects[0] = Integer.valueOf(101);
        objects[1] = "Hello, world!";
    }
}
