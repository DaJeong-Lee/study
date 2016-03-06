package Chapter13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Subsets {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(1,4,9));
		System.out.println("���:" +Subsets.subsets(list));
	}
	
	static List<List<Integer>> subsets(List<Integer> list){
		
		System.out.println("subset����");
		if(list.isEmpty()){ //�Է¸���Ʈ�� ����ִٸ� �� ����Ʈ �ڽ��� ��������
			List<List<Integer>> ans = new ArrayList<>();
			ans.add(Collections.emptyList());
			return ans;
		}
		
		//�󸮽�Ʈ�� �ƴ϶��, ù��° ��Ҹ� ������ ������ ����� ��缭�������� subans�� ����
		Integer first = list.get(0);
		List<Integer> rest = list.subList(1, list.size());
		List<List<Integer>> subans = subsets(rest);
		//subans�� first��Ҹ� �����ֱ� �� list
		System.out.println("subans : " + subans);
		
		// subans�� ��� ����Ʈ�� ó������ ��Ҹ� �տ� �߰��ؼ� ����
		List<List<Integer>> subans2 = insertAll(first, subans);
		//subans2�� subans�� first��Ҹ� �������� �� list
		System.out.println("subans2 : " + subans2);
		
		System.out.println("subset��");
		//subans�� subans2�� �����ؼ� return
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
