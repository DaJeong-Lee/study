package Chapter04;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReduceTest {
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1,2,3,4,5);
		
		//초기값이 0 
		int sum = numbers.stream().reduce(0, (a,b) -> a+b);
		
		//초기값 없을 땐 Optional 리턴
		Optional<Integer> sum2 = numbers.stream().reduce((a,b) -> a+b);
		
		// (a,b) -> a+b 대신 Integer::sum가 자바에 이미 존재함
		Optional<Integer> sum3 = numbers.stream().reduce(Integer::sum);
	}
}
