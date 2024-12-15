package 每周20道力扣.第七周;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SumTransToMulti {
    public static int getMaxTransToMulti(int[] a){
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        int temp = sum;
        int res = sum;
        for (int i = 1; i < a.length; i++) {
            temp -= a[i] + a[i-1];
            temp += a[i] * a[i-1];
            res = Math.max(temp,res);
            temp = sum;
        }
        return res;
    }
}
