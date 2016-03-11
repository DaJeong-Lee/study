package Chapter07;

import java.util.stream.Stream;

public class ParellelExample {
	public static void main(String[] args) {
		//병렬스트림 parallelSum() 작성
		System.out.println(parallelSum(100));
		
		//System.out.println(Runtime.getRuntime().availableProcessors()); //결과 :2
		//System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12"); //병렬처리 프로세서수를 12개로 세팅
		
	}
	
	public static long parallelSum(long n){
		return Stream.iterate(1L, i -> i+1)
					 .limit(n)
					 .parallel()
					 .reduce(0L, Long::sum);
	}
}
