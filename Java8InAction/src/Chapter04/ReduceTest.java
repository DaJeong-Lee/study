package Chapter04;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReduceTest {
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1,2,3,4,5);
		
		//�ʱⰪ�� 0 
		int sum = numbers.stream().reduce(0, (a,b) -> a+b);
		
		//�ʱⰪ ���� �� Optional ����
		Optional<Integer> sum2 = numbers.stream().reduce((a,b) -> a+b);
		
		// (a,b) -> a+b ��� Integer::sum�� �ڹٿ� �̹� ������
		Optional<Integer> sum3 = numbers.stream().reduce(Integer::sum);
	}
}
