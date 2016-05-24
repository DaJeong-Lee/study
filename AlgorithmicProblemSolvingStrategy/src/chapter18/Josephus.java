package chapter18;

import java.util.LinkedList;

public class Josephus {
	
	public static void kill(int n, int k){
		LinkedList<Integer> survivors = new LinkedList<>();
		
		//1~n가지 사람 초기화
		for (int i = 1; i <= n; i++) {
			survivors.add(i);
		}
		
		//죽을 사람을 나타내는 포인트 변수 kill
		int kill = 0;
		
		//2명이 남을 때 까지 반복
		while(n>2){
			survivors.remove(kill); //첫번째 사람이 자살함
			//kill이 맨 마지막+1을 포인트하고 있다면 맨 앞을 포인트하도록 옮김
			if(kill == survivors.size()){
				kill = 0;
			}
			
			n--;
			
			//k번째가 죽을 사람이므로 k-1만큼 포인터를 옮김 -> 1명이 이미 죽어서 left shift됐기 때문에 k-1번 옮김  
			for (int i = 0; i < k-1; i++) {
				kill++;
				
				//kill이 맨 마지막+1을 포인트하고 있다면 맨 앞을 포인트하도록 옮김
				if(kill == survivors.size()){
					kill = 0;
				}
			}
		}
		
		System.out.println(survivors.toString());
	}
	
	public static void main(String[] args) {
		
		int n = 6; //포위당한 사람 수 
		int k = 3; //한명이 자살한 후 k번째 사람이 자살함 
	
		Josephus.kill(n, k);
	}
}
