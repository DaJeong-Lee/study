package chapter06;

public class RecursiveSum {
	
	public int recursiveSum(int n){
		if(n == 1) return 1;
		
		return n+recursiveSum(n-1);
	}
	
	public static void main(String[] args) {
		RecursiveSum rs = new RecursiveSum();
		System.out.println(rs.recursiveSum(100));
	}
}
