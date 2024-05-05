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
		System.out.println("�Զ����࣬�Լ�����");
		// 3 �Զ����࣬�Լ�����
		Stone[] stones = new Stone[5];
		for (int i = 0; i < stones.length; i++){
			stones[i] = new Stone(arr1[i]);
		}
		Arrays.sort(stones);
		System.out.println(Arrays.toString(stones));
		
		People[] peoples = new People[5];
		for (int i = 0; i < stones.length; i++){
			peoples[i] = new People(arr1[i]);
		}
		Arrays.sort(peoples, new Comparator<People>(){
			public int compare(People p1, People p2){
				return p1.age - p2.age;
			}
		});
		System.out.println(Arrays.toString(peoples));
		Arrays.sort(peoples,(p1,p2) -> (p2.age - p1.age));
		System.out.println(Arrays.toString(peoples));
		Arrays.sort(peoples,(p1,p2) -> (p1.age - p2.age));
		System.out.println(Arrays.toString(peoples));
		Arrays.sort(peoples,Comparator.comparingInt(People::getAge));
		System.out.println(Arrays.toString(peoples));
		Arrays.sort(peoples,Comparator.comparingInt(People::getAge).reversed());
		System.out.println(Arrays.toString(peoples));
	} 
}

class Stone implements Comparable<Stone>{
	int weight;
	
	public Stone(int weight){
		this.weight = weight;
	}
	
	public int compareTo(Stone stone){
		return this.weight - stone.weight;
	}
	
	public String toString(){
		return String.valueOf(weight);
	}
}

class People{
	int age;
	
	public People(int age){
		this.age = age;
	}
	public String toString(){
		return String.valueOf(age);
	}
	
	int getAge(){
		return age;
	}
}