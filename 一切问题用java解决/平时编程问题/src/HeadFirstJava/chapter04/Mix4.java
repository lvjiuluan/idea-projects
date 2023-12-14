package HeadFirstJava.chapter04;

public class Mix4 {
    int counter = 0;

    public static void main(String[] args) {
        int[] xs = {9, 20, 7, 19};
        int[] indices = {5, 5, 7, 1};
        Mix4[] m4a = new Mix4[20];
        for (int i = 0; i < xs.length; i++) {
            int x = 0;
            int count = 0;
            while (x < xs[i]) {
                m4a[x] = new Mix4();
                m4a[x].counter = m4a[x].counter + 1;
                count = count + 1;
                count = count + m4a[x].maybeNew(x, indices[i]);
                x = x + 1;
            }
            System.out.println("x<" + xs[i] + " and " + "index<" + indices[i] + ": " + count + " " + m4a[1].counter);
        }

    }

    public int maybeNew(int index, int i) {
        if (index < i) {
            Mix4 m4 = new Mix4();
            m4.counter = m4.counter + 1;
            return 1;
        }
        return 0;
    }
}
