package chapter19;

import java.util.Stack;

public class Brackets2 {
	public static boolean wellMatched(String str){
		
		char[] formula = str.toCharArray();
		
		final String opening = "({[";
		final String closing = ")}]";
		
		Stack<Character> openStack = new Stack<>();
		
		for (int i = 0; i < formula.length; i++) {
			if(opening.indexOf(String.valueOf(formula[i])) > -1){
				//여는 괄호라면 스택에 집어 넣음
				openStack.push(formula[i]);
			}else{
				//아니라면 스택 맨 위의 문자와 맞춰보기
				//스택이 비어있는 경우는 실패
				if(openStack.empty()){
					return false;
				}
				if(opening.indexOf(String.valueOf(openStack.peek())) != closing.indexOf(String.valueOf(formula[i]))){
					return false;
				}
				
				//짝이 맞으면 스택에서 제거
				openStack.pop();
			}
		}
		
		return openStack.empty(); //모든 괄호가 닫혀야 true;
				
				
	}
	
	public static void main(String[] args) {
		System.out.println(Brackets2.wellMatched("()()}"));
		System.out.println(Brackets2.wellMatched("({[}])"));
		System.out.println(Brackets2.wellMatched("({})"));
		System.out.println(Brackets2.wellMatched("[()()]"));
	}
}
