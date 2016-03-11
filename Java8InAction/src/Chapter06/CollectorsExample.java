package Chapter06;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import Chapter04.Dish;
import java.util.IntSummaryStatistics;
import java.util.*;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.comparingInt;
import static Chapter04.Dish.*;

public class CollectorsExample {
	enum CaloricLevel { DIET, NORMAL, FAT };
	
	public static void main(String[] args) {
		//6.2 ����̰� ���
		//6.2.2 ��� ���� : �հ� ��� ���� ��ȯ�ϴ� ���� (����� ��� ���)
		//1) �հ� 
		int totalCalories = 
				menu.stream().collect(Collectors.summingInt(Dish::getCalories));
		System.out.println(totalCalories);
		
		//2) ������� (sum, averge, min, max ..)
		IntSummaryStatistics menuStatistics = 
				menu.stream().collect(summarizingInt(Dish::getCalories));
		System.out.println(menuStatistics);
		
		//6.2.3 ���ڿ� ����
		//1) ���ڿ� ����
		String shortMenu = menu.stream().map(Dish::getName).collect(joining());
		System.out.println(shortMenu); // �޴��� ��� ����Ǽ� ����
		
		shortMenu = menu.stream().map(Dish::getName).collect(joining(","));
		System.out.println(shortMenu); // �޴��ٴ� ������, �ֱ�
		
		//6.2.4 ���� ����� ��� ����
		// - collect(reducing(...)) : ��� Ưȭ�� �÷��͸� ���� ����
		
		//6.3 �׷�ȭ
		Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
		System.out.println(dishesByType); //Dish�� type�� Key�� Dish �׷���
		System.out.println(dishesByType.get(Dish.Type.MEAT).toString()); //MEAT �׷츸 ���
		
		//6.3.1 �ټ��� �׷�ȭ : �μ������� �׷�ȭ ���� 
		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
									menu.stream().collect(groupingBy(Dish::getType,
													groupingBy((Dish dish) -> {
																	if(dish.getCalories() <=400) return CaloricLevel.DIET;
																	else if(dish.getCalories() <= 700) return CaloricLevel.NORMAL;
																	else return CaloricLevel.FAT;
																}
													)));
		System.out.println(dishesByTypeCaloricLevel);
		
		//6.3.2 ����׷����� ������ ����
		//1) counting
		Map<Dish.Type, Long> typeCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
		System.out.println(typeCount); //�� �׷� ����� ������ ����
		
		//2) maxBy 
		Map<Dish.Type, Optional<Dish>> mostCaloricByType 
				= menu.stream().collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
		System.out.println(mostCaloricByType); //�� �׷캰 Į�θ��� ���� ���� ����
		
		//6.4 ���� : ��, ���� �ΰ��� ����� ��Ʈ���� ���� �� ���� 
		Map<Boolean, List<Dish>> partitionedMenu =  menu.stream().collect(partitioningBy(Dish::isVegetarian));
		System.out.println(partitionedMenu); //true, false�� �޴� ����
		
		//6.4.1 ������ ����
		// - filter�� �ص������� partitoningBy�� ��, ������ ���� �� ����
		// - �ι�° �μ��� �׷��� ����
		Map<Boolean, Map<Dish.Type, List<Dish>>> partitionedMenu2 
			=  menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
		System.out.println(partitionedMenu2); //ä�Ŀ��α��� �� type���� �׷���
		
		//6.4.2 ���ڸ� �Ҽ��� ��Ҽ��� �����ϱ�
		//�־��� ���� �Ҽ����� �Ǵ��ϴ� isPrime()�޼��� �ۼ�
		//�Ҽ��� ��Ҽ��� �����ϴ� partitionPrimes() �޼��� �ۼ�
		
		CollectorsExample ce = new CollectorsExample();
		System.out.println(ce.partitionPrimes(100));
		
		//6.5 Collector �������̽�
		//    -> ����
	}
	
	public boolean isPrime(int candidate){
		int candidateRoot = (int)Math.sqrt((double)candidate); //�־��� ���� ������ -> ����� �ҽ���� 2���� �����ٱ����� �������� ����
		return IntStream.rangeClosed(2, candidateRoot).noneMatch( i -> candidate%i == 0);
		// 2���� �����ٱ���(i)�� candidate�� �������� �� �������°� ���ٸ� �Ҽ�
	}
	
	public Map<Boolean, List<Integer>> partitionPrimes(int n){
		return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(candidate -> isPrime(candidate)));
		//2���� n���� ����(candidate)�� isPrime�� �־���� ��, �������� �з�
	}
}
