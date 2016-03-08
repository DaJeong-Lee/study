package Chapter03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/* Predicate �������̽��� java.util.function�� ���ǵǾ� ����
public interface Predicate<T>{
	boolean test(T t);
}
*/

public class PredicateExample {
	//���ʸ� <T>�� ���������ڿ� ��ȯŸ�� ���̿� ��ġ��Ŵ
	public static <T> List<T> filter(List<T> list, Predicate<T> p){
		List<T> results = new ArrayList<>();
		for(T s : list){
			if(p.test(s)){
				results.add(s);
			}
		}
		return results;
	}
	
	public static void main(String[] args) {
		Predicate<String> nonEmptyStrPredicate = (String s) -> !s.isEmpty();
		List<String> nonEmpty = filter(Arrays.asList("a","b","","d"), nonEmptyStrPredicate);
		System.out.println(nonEmpty.toString());
		
		
	}
}
