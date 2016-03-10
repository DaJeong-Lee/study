package Chapter05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class StreamPractice {

	public static void main(String[] args) {
		Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
		
		List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300), 
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),	
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );	
		
		//문제1. 2011년에 일어난 모든 트랜젹션을 찾아 값을 오름차순으로 정렬하시오
		List<Transaction> q1 = question1(transactions);
		System.out.println("문제1 : "+ q1.toString());
		
		//문제2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
		List<String> q2 = question2(transactions);
		System.out.println("문제2 : "+ q2.toString());
		
		//문제3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
		List<Trader> q3 = question3(transactions);
		System.out.println("문제3 : "+ q3.toString());
		
		//문제4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
		String q4 = question4(transactions);
		System.out.println("문제4 : "+ q4);
		
		//문제5. 밀라노에 거래자가 있는가?
		boolean q5 = question5(transactions);
		System.out.println("문제5 : "+ q5);
		
		//문제6. 케임브리지에 거주하는 거래자의 모든 트랜젝션값을 출력하시오.
		question6(transactions);
		
		//문제7. 전체 트랜젝션 중 최댓값은 얼마인가?
		Optional<Integer> q7 = question7(transactions);
		System.out.println("문제7 : "+ q7.toString());
		
		//문제8. 전체 트랜젝션 중 최소값은 얼마인가?
		Optional<Transaction> q8 = question8(transactions);
		System.out.println("문제7 : "+ q8.get().getValue());
	}
	
	public static List<Transaction> question1(List<Transaction> transactions){
		List<Transaction> list = transactions.stream().filter( t -> t.getYear() == 2011)
							 						  .sorted(Comparator.comparing(Transaction::getValue))
							 						  .collect(Collectors.toList());
		
		return list;
	}
	
	public static List<String> question2(List<Transaction> transactions){
		List<String> list = transactions.stream()
										.map(t -> t.getTrader().getCity())
										.distinct()
										.collect(toList());
		
		return list;
		
	}
	
	public static List<Trader> question3(List<Transaction> transactions){
		List<Trader> list = 
							transactions.stream()
							.map( transaction -> transaction.getTrader()) //Transaction::getTrader 도 가능
							.filter(trader -> trader.getCity().equals("Cambridge"))
							.distinct()
							.sorted(comparing(Trader::getName))
							.collect(toList());
		return list;
	}
	
	public static String question4(List<Transaction> transactions){
		String result = 
						transactions.stream()
						.map(transaction -> transaction.getTrader().getName())
						.distinct()
						.sorted()
						.reduce("", (n1, n2) -> n1+", "+n2);
		
		return result;
		
	}
	
	public static boolean question5(List<Transaction> transactions){
		boolean result = 
							transactions
							.stream()
							.anyMatch( t-> t.getTrader().getCity().equals("Milan"));

		return result;
	}
	
	public static void question6(List<Transaction> transactions){
		transactions.stream()
					.filter(t -> t.getTrader().getCity().equals("Cambridge"))
					.map(Transaction::getValue)
					.forEach(System.out::println);
	}
	
	public static Optional<Integer> question7(List<Transaction> transactions){
		Optional<Integer> result = 
									transactions.stream()
												.map(Transaction::getValue)
												.reduce(Integer::max);
		
		return result;
	}
	
	public static Optional<Transaction> question8(List<Transaction> transactions){
		Optional<Transaction> result =
										transactions.stream()
													.min(comparing(Transaction::getValue));
		return result;
	}
	
}
