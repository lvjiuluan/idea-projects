package 韩顺平Java.字符串综合.StringBuilder_;

public class StringBuilder01 {
    public static void timeConsume(Object obj) {
        long startTime = System.currentTimeMillis();
        if (obj instanceof String) {
            String s = (String) obj;
            for (int i = 0; i < 20000; i++) {
                s += i;
            }
        } else {
            for (int i = 0; i < 20000; i++) {
                if (obj instanceof StringBuilder) {
                    ((StringBuilder) obj).append(i);
                } else {
                    ((StringBuffer) obj).append(i);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println(obj.getClass().getName() + ": " + (endTime - startTime));
    }

    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        String string = new String();
        timeConsume(stringBuilder);
        timeConsume(stringBuilder);
        timeConsume(string);
    }
}
