package chapter06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Picnic {
	int n;
	boolean[][] areFriends = new boolean[10][10];
	
	public int countPairings(ArrayList<Boolean> taken){
		
		boolean finished = true;
		for (int i = 0; i < n; i++) {
			if (!taken.get(i)) {
				//taken은 i번째 학생이 짝을 찾았는지 여부
				//한 학생이라도 짝이 없으면 종료하지 않음
				finished = false;
			}
		}

		if (finished) {
			return  1;
		}
		
		int result=0;
		
		//서로 친구인 두 학생을 찾아 짝을 지어준다.
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(!taken.get(i)&&!taken.get(j)&&areFriends[i][j]){
					taken.set(i, true);
					taken.set(j, true);
					result += countPairings(taken);
					taken.set(i, false);
					taken.set(j, false);
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		Picnic picnic = new Picnic();
		picnic.n = 4;
		int[] list = {0,1,1,2,2,3,3,0,0,2,1,3};
		
		for (int i = 0; i < list.length-1; i+=2) {
			picnic.areFriends[list[i]][list[i+1]] = true;
			picnic.areFriends[list[i+1]][list[i]] = true;
		}
		
		System.out.println(picnic.countPairings(new ArrayList<Boolean>(Arrays.asList(false,false,false,false,false,false,false,false,false,false))));
		
		
	}
}
