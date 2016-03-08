package Chapter03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/* Predicate 인터페이스는 java.util.function에 정의되어 있음
public interface Predicate<T>{
	boolean test(T t);
}
*/

public class PredicateExample {
	//제너릭 <T>는 접근지시자와 반환타입 사이에 위치시킴
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
