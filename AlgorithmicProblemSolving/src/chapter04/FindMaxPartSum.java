package chapter04;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FindMaxPartSum {
	
	//가장 비효율적인 방법: O(N^3)
	public int inefficientMaxSum(ArrayList<Integer> numbers){
		int N = numbers.size();
		int result = Collections.min(numbers);

		for (int i = 0; i < N; i++) {
			for (int j = i; j < N; j++) {
				int sum = 0;
				for (int k = i; k <= j; k++) {
					sum += numbers.get(k);
				}
				result = Math.max(result, sum);
			}
		}
		return result;
	}
	
	//좀더 나은 방법 (이전 이동평균 구했던것과 비슷하게): O(N^2)
	public int betterMaxSum(ArrayList<Integer> numbers){
		int N = numbers.size();
		int result = Collections.min(numbers);

		for (int i = 0; i < N; i++) {
			int sum = 0;
			for (int j = i; j < N; j++) {
				sum += numbers.get(j);
				result = Math.max(result, sum);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		FindMaxPartSum fmps = new FindMaxPartSum();
		
		Integer[] a = {-7,4,-3,6,3,-8,3,4};
		ArrayList<Integer> array = new ArrayList<Integer>(Arrays.asList(a));		
		
		//정답은 10 {4,-3,6,3} 구간
		System.out.println(fmps.inefficientMaxSum(array));
		System.out.println(fmps.betterMaxSum(array));
	}
	
}
