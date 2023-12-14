package 每周20道力扣.第四周;

public class Pow {
    public static double myPow(double x, int n) {
        double res = 1.0;
        if(n > 0){
            while(n != 0){
                if((n & 1) == 1) res *= x;
                x *= x;
                n >>>= 1;
            }
        }else{
            n = -n;
            while(n != 0){
                if((n & 1) == 1) res /= x;
                x *= x;
                n >>>= 1;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(myPow(2,-2));
    }
}
