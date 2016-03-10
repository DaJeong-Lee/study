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
		
		//����1. 2011�⿡ �Ͼ ��� Ʈ�������� ã�� ���� ������������ �����Ͻÿ�
		List<Transaction> q1 = question1(transactions);
		System.out.println("����1 : "+ q1.toString());
		
		//����2. �ŷ��ڰ� �ٹ��ϴ� ��� ���ø� �ߺ� ���� �����Ͻÿ�.
		List<String> q2 = question2(transactions);
		System.out.println("����2 : "+ q2.toString());
		
		//����3. ���Ӻ긮������ �ٹ��ϴ� ��� �ŷ��ڸ� ã�Ƽ� �̸������� �����Ͻÿ�.
		List<Trader> q3 = question3(transactions);
		System.out.println("����3 : "+ q3.toString());
		
		//����4. ��� �ŷ����� �̸��� ���ĺ������� �����ؼ� ��ȯ�Ͻÿ�.
		String q4 = question4(transactions);
		System.out.println("����4 : "+ q4);
		
		//����5. �ж�뿡 �ŷ��ڰ� �ִ°�?
		boolean q5 = question5(transactions);
		System.out.println("����5 : "+ q5);
		
		//����6. ���Ӻ긮���� �����ϴ� �ŷ����� ��� Ʈ�����ǰ��� ����Ͻÿ�.
		question6(transactions);
		
		//����7. ��ü Ʈ������ �� �ִ��� ���ΰ�?
		Optional<Integer> q7 = question7(transactions);
		System.out.println("����7 : "+ q7.toString());
		
		//����8. ��ü Ʈ������ �� �ּҰ��� ���ΰ�?
		Optional<Transaction> q8 = question8(transactions);
		System.out.println("����7 : "+ q8.get().getValue());
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
							.map( transaction -> transaction.getTrader()) //Transaction::getTrader �� ����
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
