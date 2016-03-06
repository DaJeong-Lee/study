package Chapter13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Subsets {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(1,4,9));
		System.out.println("결과:" +Subsets.subsets(list));
	}
	
	static List<List<Integer>> subsets(List<Integer> list){
		
		System.out.println("subset시작");
		if(list.isEmpty()){ //입력리스트가 비어있다면 빈 리스트 자신이 서브집합
			List<List<Integer>> ans = new ArrayList<>();
			ans.add(Collections.emptyList());
			return ans;
		}
		
		//빈리스트가 아니라면, 첫번째 요소를 꺼내고 나머지 요소의 모든서브집합을 subans로 전달
		Integer first = list.get(0);
		List<Integer> rest = list.subList(1, list.size());
		List<List<Integer>> subans = subsets(rest);
		//subans는 first요소를 끼워넣기 전 list
		System.out.println("subans : " + subans);
		
		// subans의 모든 리스트에 처음꺼낸 요소를 앞에 추가해서 만듬
		List<List<Integer>> subans2 = insertAll(first, subans);
		//subans2는 subans에 first요소를 끼워넣은 후 list
		System.out.println("subans2 : " + subans2);
		
		System.out.println("subset끝");
		//subans와 subans2를 연결해서 return
		return concat(subans, subans2);
		
	}
	
	static List<List<Integer>> insertAll(Integer first, List<List<Integer>> lists){
		List<List<Integer>> result = new ArrayList<>();
		for(List<Integer> list : lists){
			List<Integer> copyList = new ArrayList<>();
			copyList.add(first);
			copyList.addAll(list);
			result.add(copyList);
		}
		
		return result;
	}
	
	static List<List<Integer>> concat(List<List<Integer>> a, List<List<Integer>> b){
		List<List<Integer>> r = new ArrayList<>(a);
		r.addAll(b);
		return r;
	}
	
}
