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
		//6.2 리듀싱과 요약
		//6.2.2 요약 연산 : 합계 평균 등을 반환하는 연산 (리듀싱 기능 사용)
		//1) 합계 
		int totalCalories = 
				menu.stream().collect(Collectors.summingInt(Dish::getCalories));
		System.out.println(totalCalories);
		
		//2) 통계정보 (sum, averge, min, max ..)
		IntSummaryStatistics menuStatistics = 
				menu.stream().collect(summarizingInt(Dish::getCalories));
		System.out.println(menuStatistics);
		
		//6.2.3 문자열 연결
		//1) 문자열 연결
		String shortMenu = menu.stream().map(Dish::getName).collect(joining());
		System.out.println(shortMenu); // 메뉴가 모두 연결되서 나옴
		
		shortMenu = menu.stream().map(Dish::getName).collect(joining(","));
		System.out.println(shortMenu); // 메뉴바다 구분자, 넣기
		
		//6.2.4 범용 리듀싱 요약 연산
		// - collect(reducing(...)) : 모든 특화된 컬렉터를 구현 가능
		
		//6.3 그룹화
		Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
		System.out.println(dishesByType); //Dish의 type을 Key로 Dish 그룹핑
		System.out.println(dishesByType.get(Dish.Type.MEAT).toString()); //MEAT 그룹만 출력
		
		//6.3.1 다수준 그룹화 : 두수분으로 그룹화 가능 
		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
									menu.stream().collect(groupingBy(Dish::getType,
													groupingBy((Dish dish) -> {
																	if(dish.getCalories() <=400) return CaloricLevel.DIET;
																	else if(dish.getCalories() <= 700) return CaloricLevel.NORMAL;
																	else return CaloricLevel.FAT;
																}
													)));
		System.out.println(dishesByTypeCaloricLevel);
		
		//6.3.2 서브그룹으로 데이터 수집
		//1) counting
		Map<Dish.Type, Long> typeCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
		System.out.println(typeCount); //각 그룹 요소의 갯수를 리턴
		
		//2) maxBy 
		Map<Dish.Type, Optional<Dish>> mostCaloricByType 
				= menu.stream().collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
		System.out.println(mostCaloricByType); //각 그룹별 칼로리가 제일 옾은 음식
		
		//6.4 분할 : 참, 거짓 두가지 요소의 스트림을 가질 수 있음 
		Map<Boolean, List<Dish>> partitionedMenu =  menu.stream().collect(partitioningBy(Dish::isVegetarian));
		System.out.println(partitionedMenu); //true, false로 메뉴 구분
		
		//6.4.1 분할의 장점
		// - filter로 해도되지만 partitoningBy는 참, 거짓을 얻을 수 있음
		// - 두번째 인수로 그룹핑 가능
		Map<Boolean, Map<Dish.Type, List<Dish>>> partitionedMenu2 
			=  menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
		System.out.println(partitionedMenu2); //채식여부구분 후 type으로 그룹핑
		
		//6.4.2 숫자를 소수와 비소수로 분할하기
		//주어진 수가 소수인지 판단하는 isPrime()메서드 작성
		//소수와 비소수를 분할하는 partitionPrimes() 메서드 작성
		
		CollectorsExample ce = new CollectorsExample();
		System.out.println(ce.partitionPrimes(100));
		
		//6.5 Collector 인터페이스
		//    -> 생략
	}
	
	public boolean isPrime(int candidate){
		int candidateRoot = (int)Math.sqrt((double)candidate); //주어진 수의 제곱근 -> 어떤수가 소스라면 2부터 제곱근까지로 나눠지지 않음
		return IntStream.rangeClosed(2, candidateRoot).noneMatch( i -> candidate%i == 0);
		// 2부터 제곱근까지(i)로 candidate를 나눠봤을 때 나눠지는게 없다면 소수
	}
	
	public Map<Boolean, List<Integer>> partitionPrimes(int n){
		return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(candidate -> isPrime(candidate)));
		//2부터 n까지 숫자(candidate)를 isPrime에 넣어봐서 참, 거짓으로 분류
	}
}
