package chapter06;

import java.util.ArrayList;

public class RecursivePick {
	public void nonRecursive(int n){
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					for (int l = 0; l < n; l++) {
						System.out.println(i+" "+j+" "+k+" "+l+" ");
					}
				}
			}
		}
		
	}
	
	//조합: 순서없이 골라낸 것 
	//n: 전체 원소수
	//picked: 지금까지 고른 원소들의 번호
	//toPick: 더 고를 원소의 수
	//일때, 앞으로 toPick개의 원소를 고르는 모든 방법을 출력한다.
	public void pick(int n, ArrayList<Integer> picked, int toPick){
		
		//base case(더 쪼개지지 않는 작업): 더 고를 원소가 없을 때 고른 원소들을 출력한다.
		if(toPick == 0){
			printPicked(picked);
			return;
		}
		
		//고를 수 있는 가장 작은 번호를 계산한다.
		int smallest = picked.isEmpty()?0:picked.get(picked.size()-1);
		
		//이 단계에서 원소 하나를 고른다.
		for (int next = smallest; next < n; next++) {
			picked.add(next);
			pick(n, picked, toPick-1);
			picked.remove(picked.size()-1);
		}
		
		
	}
	
	
	
	private void printPicked(ArrayList<Integer> picked) {
		System.out.println(picked.toString());
	}

	public static void main(String[] args) {
		RecursivePick rp = new RecursivePick();
//		rp.nonRecursive(5);
		
		rp.pick(5, new ArrayList<>(), 4);
		
		
	}
}
