package 每周20道力扣.第六周.string_;

import java.util.Arrays;
import java.util.Comparator;

public class ComparatorImplTest {
    public static void main(String[] args) {
        Stone[] stones = new Stone[10];
        for (int i = 0; i < 10; i++) {
            stones[i] = new Stone(i);
        }
        Arrays.sort(stones, new StoneComparator());
        System.out.println(Arrays.asList(stones));
        Arrays.sort(stones, new Comparator<Stone>() {
            @Override
            public int compare(Stone o1, Stone o2) {
                return o1.weight - o2.weight;
            }
        });
        Comparator<Stone> stoneComparator = Comparator.comparingInt(o -> o.weight);
        Comparator<Stone> reversed = stoneComparator.reversed();
        Arrays.sort(stones, reversed);
        System.out.println(Arrays.asList(stones));
    }
}

class StoneComparator implements Comparator<Stone> {

    @Override
    public int compare(Stone o1, Stone o2) {
        return o1.weight - o2.weight;
    }
}
