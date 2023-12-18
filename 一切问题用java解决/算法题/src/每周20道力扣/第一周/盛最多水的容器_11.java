package 每周20道力扣.第一周;

public class 盛最多水的容器_11 {
    public static int caculate(int start, int end, int[] height) {
        return (end - start) * Math.min(height[start], height[end]);
    }

    public static int maxArea(int[] height) {
        int start = 0;
        int end = height.length - 1;
        int maxA = caculate(start, end, height);
        while (start < end) {
            if (height[start] > height[end]) {
                end--;
                maxA = Math.max(maxA, caculate(start, end, height));
            } else {
                start++;
                maxA = Math.max(maxA, caculate(start, end, height));
            }
        }
        return maxA;
    }

    public static void main(String[] args) {
        int[] height = {1,1};
        System.out.println(maxArea(height));;
    }
}
