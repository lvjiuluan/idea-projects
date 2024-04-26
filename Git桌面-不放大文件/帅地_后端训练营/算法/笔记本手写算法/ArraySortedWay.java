import java.util.Arrays;
import java.util.Comparator;

public class ArraySortedWay{
	public static void main(String[] args){
		int[] arr1 = new int[]{2,3,1,5,4};
		// 1 基本数据类型排序
		// 1.1 升序（默认）
		Arrays.sort(arr1);
		System.out.println(Arrays.toString(arr1));
		// 1.2 降序
		// 基本数据类型无法使用Comparator，所以用对应的包装类
		Integer[] arr2 = new Integer[]{2,3,1,5,4};
		Arrays.sort(arr2); // 调用的是 sort(Object[] a),由于Integer实现了Compareable接口，使用compareTo方法比较大小，升序。
		System.out.println(Arrays.toString(arr2));
		arr2 = new Integer[]{2,3,1,5,4};
		Arrays.sort(arr2,Comparator.reverseOrder()); 
		System.out.println(Arrays.toString(arr2));
		// 3 自定义类，自己创建
		
	} 
}