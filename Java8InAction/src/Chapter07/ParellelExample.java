package Chapter07;

import java.util.stream.Stream;

public class ParellelExample {
	public static void main(String[] args) {
		//���Ľ�Ʈ�� parallelSum() �ۼ�
		System.out.println(parallelSum(100));
		
		//System.out.println(Runtime.getRuntime().availableProcessors()); //��� :2
		//System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12"); //����ó�� ���μ������� 12���� ����
		
	}
	
	public static long parallelSum(long n){
		return Stream.iterate(1L, i -> i+1)
					 .limit(n)
					 .parallel()
					 .reduce(0L, Long::sum);
	}
}
