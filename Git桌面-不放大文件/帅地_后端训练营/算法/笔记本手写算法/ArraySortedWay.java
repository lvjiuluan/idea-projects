import java.util.Arrays;
import java.util.Comparator;

public class ArraySortedWay{
	public static void main(String[] args){
		int[] arr1 = new int[]{2,3,1,5,4};
		// 1 ����������������
		// 1.1 ����Ĭ�ϣ�
		Arrays.sort(arr1);
		System.out.println(Arrays.toString(arr1));
		// 1.2 ����
		// �������������޷�ʹ��Comparator�������ö�Ӧ�İ�װ��
		Integer[] arr2 = new Integer[]{2,3,1,5,4};
		Arrays.sort(arr2); // ���õ��� sort(Object[] a),����Integerʵ����Compareable�ӿڣ�ʹ��compareTo�����Ƚϴ�С������
		System.out.println(Arrays.toString(arr2));
		arr2 = new Integer[]{2,3,1,5,4};
		Arrays.sort(arr2,Comparator.reverseOrder()); 
		System.out.println(Arrays.toString(arr2));
		// 3 �Զ����࣬�Լ�����
		
	} 
}