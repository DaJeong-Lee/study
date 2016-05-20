package chapter04;

import java.util.ArrayList;

public class IntegerFactorization {
	//소인수 분해 알고리즘
	public ArrayList<Integer> factor(int n){
		ArrayList<Integer> result = new ArrayList<>();
		
		if(n == 1){
			result.add(1);
			return result;
		}
		
		for(int div = 2; n > 1; div++){
			while(n%div == 0){
				n /= div;
				result.add(div);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		IntegerFactorization ifr = new IntegerFactorization();
		System.out.println(ifr.factor(24).toString());
	}
}
